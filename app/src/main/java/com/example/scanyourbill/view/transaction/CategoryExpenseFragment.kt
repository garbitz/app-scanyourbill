package com.example.scanyourbill.view.transaction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.FragmentCategoryExpenseBinding
import com.example.scanyourbill.view.ViewModelFactory

class CategoryExpenseFragment : Fragment() {

    private var _binding: FragmentCategoryExpenseBinding? = null
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
        _binding = FragmentCategoryExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clothingBtn.setOnClickListener {
            onCategorySelected("clothing", "outcome")
        }
        binding.foodBtn.setOnClickListener {
            onCategorySelected("food", "outcome")
        }
        binding.stationeryBtn.setOnClickListener {
            onCategorySelected("stationery", "outcome")
        }
        binding.toiletriesBtn.setOnClickListener {
            onCategorySelected("toiletries", "outcome")
        }
        binding.medicalBtn.setOnClickListener {
            onCategorySelected("medical", "outcome")
        }
        binding.entertainmentBtn.setOnClickListener {
            onCategorySelected("entertainment", "outcome")
        }
        binding.othersBtn.setOnClickListener {
            onCategorySelected("others", "outcome")
        }
    }

    private fun onCategorySelected(categoryId: String, activityType: String) {
        (requireActivity() as TransactionActivity).handleCategorySelected(categoryId, activityType)
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