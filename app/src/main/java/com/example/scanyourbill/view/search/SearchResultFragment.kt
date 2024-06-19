package com.example.scanyourbill.view.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.FragmentSearchResultBinding
import com.example.scanyourbill.databinding.ItemSearchResultBinding
import com.example.scanyourbill.databinding.ItemTopSpendingBinding
import com.example.scanyourbill.formatNumber
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.main.MainActivity
import com.example.scanyourbill.view.transaction.TransactionViewModel
import id.derysudrajat.easyadapter.EasyAdapter
import id.derysudrajat.easyadapter.EasyAdapterIndexed

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val viewModel by activityViewModels<SearchViewModel>()
    private lateinit var binding: FragmentSearchResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchResult.layoutManager = layoutManager



        viewModel.searchResult.observe(viewLifecycleOwner) { response ->

            val listResult = response.data?.filterNotNull() ?: emptyList()


            binding.rvSearchResult.adapter = EasyAdapterIndexed(listResult, ItemSearchResultBinding::inflate) { binding, data, index ->
                binding.itemDate.text = data.date
                binding.itemAmount.text = buildString {
                    append("Rp")
                    append(data.amount!!)
                }

                binding.itemNotes.text = data.notes

                binding.root.setOnClickListener {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }

            }


        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}