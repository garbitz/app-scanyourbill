package com.example.scanyourbill.view.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scanyourbill.databinding.ActivityMainBinding
import com.example.scanyourbill.view.wallet.WalletActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ir.mahozad.android.PieChart


class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomSheet = binding.mainBottomSheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED;
        val buttonWallet = binding.walletBtn
        buttonWallet.setOnClickListener{
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
        }

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