package com.example.scanyourbill.view.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanyourbill.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.derysudrajat.easyadapter.EasyAdapter
import ir.mahozad.android.PieChart
import com.example.scanyourbill.databinding.ItemTopSpendingBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.wallet.WalletActivity


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvTopSpending.layoutManager = layoutManager

        viewModel.homeResult.observe(this) { response ->
            val listTopSpending = response.data?.topActivities?.filterNotNull() ?: emptyList()
            binding.rvTopSpending.adapter = EasyAdapter(listTopSpending, ItemTopSpendingBinding::inflate) { binding, data ->
                binding.tvCategory.text = data.category

            }
        }

        viewModel.getHome("2024-06")




        val bottomSheet = binding.mainBottomSheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED;

        val pieChart = binding.pieChart
        pieChart.apply {
            slices = listOf(
                PieChart.Slice(
                    0.3f,
                    Color.rgb(120, 181, 0),
                    Color.rgb(149, 224, 0),
                    legend = "Legend A"
                ),
                PieChart.Slice(
                    0.2f,
                    Color.rgb(204, 168, 0),
                    Color.rgb(249, 228, 0),
                    legend = "Legend B"
                ),
                PieChart.Slice(
                    0.2f,
                    Color.rgb(0, 162, 216),
                    Color.rgb(31, 199, 255),
                    legend = "Legend C"
                ),
                PieChart.Slice(
                    0.17f,
                    Color.rgb(255, 4, 4),
                    Color.rgb(255, 72, 86),
                    legend = "Legend D"
                ),
                PieChart.Slice(
                    0.13f,
                    Color.rgb(160, 165, 170),
                    Color.rgb(175, 180, 185),
                    legend = "Legend E"
                )
            )

        }



    }
}