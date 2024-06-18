package com.example.scanyourbill.view.transaction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.scanyourbill.R
import com.example.scanyourbill.TransactionThisMonthViewModel
import com.example.scanyourbill.databinding.FragmentCategoryIncomeBinding
import com.example.scanyourbill.view.ViewModelFactory

class CategoryIncomeFragment : Fragment() {

    private var _binding: FragmentCategoryIncomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().applicationContext))
            .get(TransactionViewModel::class.java)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.salaryBtn.setOnClickListener {
            onCategorySelected("salary")
        }
        binding.incTransfer.setOnClickListener {
            onCategorySelected("incoming_transfer")
        }
        binding.dividendBox.setOnClickListener {
            onCategorySelected("dividend")
        }
    }

    private fun onCategorySelected(categoryId: String) {
        (requireActivity() as TransactionActivity).handleCategorySelected(categoryId)
        closeFragment()
    }

    private fun closeFragment() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
