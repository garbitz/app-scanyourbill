package com.example.scanyourbill

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.databinding.FragmentTransactionFutureBinding
import com.example.scanyourbill.databinding.FragmentTransactionThisMonthBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.transaction.TransactionParentAdapter

class TransactionFutureFragment : Fragment() {

    private lateinit var viewModel: TransactionThisMonthViewModel
    private lateinit var parentAdapter: TransactionParentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTransactionFutureBinding.inflate(inflater, container, false)
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

        // Fetch data
        viewModel.getTransactions("2024-05-01", "2024-05-30", true, "all")
    }
}