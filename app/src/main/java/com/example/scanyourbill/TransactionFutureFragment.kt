package com.example.scanyourbill

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanyourbill.databinding.FragmentTransactionFutureBinding
import com.example.scanyourbill.databinding.FragmentTransactionThisMonthBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.transaction.TransactionParentAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TransactionFutureFragment : Fragment() {

    private lateinit var viewModel: TransactionThisMonthViewModel
    private lateinit var parentAdapter: TransactionParentAdapter
    private lateinit var binding: FragmentTransactionFutureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionFutureBinding.inflate(inflater, container, false)
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

        viewModel.sum.observe(viewLifecycleOwner) { sum ->
            Log.d("TransactionFragment", "Observer triggered with data: ${sum}")
            binding.summaryVal.text = formatCurrency("Rp", sum["sum"]!!)
            binding.inflowVal.text = formatCurrency("Rp", sum["inflow"]!!)
            binding.outflowVal.text = formatCurrency("Rp", sum["outflow"]!!)

            if (sum["sum"]!! > 0) {
                binding.summaryVal.setTextColor(ContextCompat.getColor((requireContext()), R.color.green_income))
            } else {
                binding.summaryVal.setTextColor(ContextCompat.getColor((requireContext()), R.color.red_outcome))
            }
        }

        val (startDate, endDate) = getCurrentMonthDates()

        // Fetch data
        viewModel.getTransactions(startDate, endDate, true, "all")
    }

    private fun getCurrentMonthDates(): Pair<String, String> {
        var currentDate = LocalDate.parse(requireActivity().intent.getStringExtra("date"))
        currentDate = currentDate.plusMonths(1)

        val startOfMonth = currentDate.withDayOfMonth(1)
        val endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return Pair(startOfMonth.format(formatter), endOfMonth.format(formatter))
    }
}