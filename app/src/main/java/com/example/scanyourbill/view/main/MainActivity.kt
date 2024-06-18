package com.example.scanyourbill.view.main

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import by.dzmitry_lakisau.month_year_picker_dialog.MonthYearPickerDialog
import com.example.scanyourbill.R
import com.example.scanyourbill.data.response.TopActivitiesItem
import com.example.scanyourbill.data.response.TopUsedWalletsItem
import com.example.scanyourbill.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.derysudrajat.easyadapter.EasyAdapter
import ir.mahozad.android.PieChart
import com.example.scanyourbill.databinding.ItemTopSpendingBinding
import com.example.scanyourbill.formatCurrency
import com.example.scanyourbill.formatNumber
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.wallet.WalletActivity
import com.example.scanyourbill.view.transaction.TransactionActivity
import com.example.scanyourbill.view.search.SearchActivity
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>{
        ViewModelFactory.getInstance(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private var today = LocalDate.now()

    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvTopSpending.layoutManager = layoutManager

        val layoutManager2 = LinearLayoutManager(this)
        binding.rvTopWallet.layoutManager = layoutManager2

        binding.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.tvSeeAllWallet.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
        }

        binding.btnAddActivity.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
        }

        binding.layoutMonth.setOnClickListener {

            Log.d("MainActivity", "Month")


            val dialog = MonthYearPickerDialog.Builder(
                context = this,
                onDateSetListener = { year, month ->
                    // do something
                    Log.d("MainActivity", "year: $year, month: $month")
                    val dateString = "$year-${String.format("%02d", month + 1)}"
                    viewModel.getHome(dateString)
                    val todayString = "$dateString-01"
                    today = LocalDate.parse(todayString)
                    Log.d("MainActivityOnDateSet", "today: $today")
                    setupMonthText(todayString)

                },
                themeResId = R.style.Style_MonthYearPickerDialog_Orange,
                selectedYear = 2024,
                selectedMonth = 1
            ).build()

            dialog.setTitle("Select month and year")

            MonthPickerDialogFragment.newInstance(dialog)
                .showNow(supportFragmentManager, MonthPickerDialogFragment::class.java.simpleName)
        }


        viewModel.homeResult.observe(this) { response ->
            val data = response.data

            setupTopSpendingAdapter(data?.topActivities?.filterNotNull() ?: emptyList())
            setupTopWalletAdapter(data?.topUsedWallets?.filterNotNull() ?: emptyList())
            setupAmountTexts(data?.income ?: 0, data?.outcome ?: 0, data?.monthSummary ?: 0)
            setupPieChart(data?.income ?: 0, data?.outcome ?: 0)
        }

        Log.d("MainActivityOnCreate", "today: $today")

        viewModel.getHome(today.toString())
        setupMonthText(today.toString())



        val bottomSheet = binding.mainBottomSheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED;





    }
    private fun setupTopSpendingAdapter(listTopSpending: List<TopActivitiesItem>) {
        binding.rvTopSpending.adapter = EasyAdapter(listTopSpending, ItemTopSpendingBinding::inflate) { binding, data ->
            binding.tvCategory.text = data.category

            val prefix = if (data.activityType == false) "-Rp" else "Rp"
            binding.tvAmount.text = buildString {
                append(prefix)
                append(formatNumber(data.amount!!))
            }
        }
    }

    private fun setupTopWalletAdapter(listTopWallet: List<TopUsedWalletsItem>) {
        binding.rvTopWallet.adapter = EasyAdapter(listTopWallet, ItemTopSpendingBinding::inflate) { binding, data ->
            binding.tvCategory.text = data.name
            val prefix = "Rp"

            binding.tvAmount.text = buildString {
                append(prefix)
                append(formatNumber(data.balance!!))
            }
        }
    }

    private fun setupAmountTexts(income: Int, outcome: Int, monthSummary: Int) {
        val prefix = "Rp"
        binding.tvIncomeAmount.text = buildString {
            append(prefix)
            append(formatNumber(income))
        }
        binding.tvExpenseAmount.text = buildString {
            append(prefix)
            append(formatNumber((outcome)))
        }
        binding.tvMonthSummary.text = buildString {
            append("Balance: ")
            append(formatCurrency("Rp", monthSummary))
        }
        if (monthSummary > 0) {
            binding.tvMonthSummary.setTextColor(ContextCompat.getColor(this, R.color.green_income))
        } else {
            binding.tvMonthSummary.setTextColor(ContextCompat.getColor(this, R.color.red_outcome))
        }
    }

    private fun setupPieChart(income: Int, outcome: Int) {
        val total = income + outcome
        val incomeFraction = income.toFloat() / total
        val outcomeFraction = outcome.toFloat() / total

        val pieChart = binding.pieChart
        pieChart.apply {
            slices = listOf(
                PieChart.Slice(
                    incomeFraction,
                    Color.parseColor("#8000C7"),
                    legend = "Income"
                ),
                PieChart.Slice(
                    outcomeFraction,
                    Color.rgb(255, 0, 0),
                    legend = "Expense"
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupMonthText(date: String) {
        var fullDate = date
        if (date.length == 7) {
            fullDate = "$date-01"
        }

        // Parse the full date
        val localDate = LocalDate.parse(fullDate)
        val monthText = binding.tvMonth
        val formatter = DateTimeFormatter.ofPattern("MMMM, yyyy")
        monthText.text = localDate.format(formatter)
    }
}