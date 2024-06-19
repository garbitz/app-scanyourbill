package com.example.scanyourbill.view.scanbill

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.FragmentEditBillItemBinding

class EditBillItemFragment : Fragment() {

    private var _binding: FragmentEditBillItemBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: BillChildAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBillItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemTitle = arguments?.getString("title") ?: ""
        val itemPrice = arguments?.getInt("price") ?: 0

        binding.editTitle.setText(itemTitle)
        binding.editPrice.setText(itemPrice.toString())

        val position = arguments?.getInt("position") ?: 0

        binding.saveButton.setOnClickListener {
            val newTitle = binding.editTitle.text.toString()
            val newPrice = binding.editPrice.text.toString().toIntOrNull() ?: 0
            val position = arguments?.getInt("position") ?: 0

            // Call the onItemUpdated method of the adapter
            adapter.onItemUpdated(newTitle, newPrice, position)

            parentFragmentManager.popBackStack()
        }

        binding.cancelButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




