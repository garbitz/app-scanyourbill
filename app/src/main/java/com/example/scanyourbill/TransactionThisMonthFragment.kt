package com.example.scanyourbill

import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.databinding.ActivityListTransactionBinding
import com.example.scanyourbill.databinding.ActivityMainBinding
import com.example.scanyourbill.databinding.FragmentTransactionThisMonthBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.transaction.TransactionParentAdapter
import com.example.scanyourbill.view.wallet.WalletViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TransactionThisMonthFragment : Fragment() {

    private lateinit var viewModel: TransactionThisMonthViewModel
    private lateinit var parentAdapter: TransactionParentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTransactionThisMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().applicationContext))
            .get(TransactionThisMonthViewModel::class.java)

        // Initialize RecyclerView
        val parentRecyclerView: RecyclerView = view.findViewById(R.id.rvTransactionParent)
        parentRecyclerView.layoutManager = LinearLayoutManager(context)
        parentAdapter = TransactionParentAdapter(emptyList())
        parentRecyclerView.adapter = parentAdapter

        // Observe LiveData from ViewModel
        viewModel.result.observe(viewLifecycleOwner) { transactionResponse ->
            Log.d("TransactionFragment", "Observer triggered with data: ${transactionResponse.data}")
            transactionResponse.data?.let {
                parentAdapter.updateData(it)
            }
        }

        val (startDate, endDate) = getCurrentMonthDates()

        // Fetch data
        viewModel.getTransactions(startDate, endDate, true, "all")
    }

    private fun getCurrentMonthDates(): Pair<String, String> {
        val currentDate = LocalDate.now()
        val startOfMonth = currentDate.withDayOfMonth(1)
        val endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return Pair(startOfMonth.format(formatter), endOfMonth.format(formatter))
    }
}

