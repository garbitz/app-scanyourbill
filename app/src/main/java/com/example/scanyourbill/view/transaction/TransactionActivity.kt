package com.example.scanyourbill.view.transaction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import com.example.scanyourbill.ListTransactionActivity
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.ActivityTransactionBinding
import com.example.scanyourbill.view.BaseActivity
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.wallet.WalletActivity
import com.example.scanyourbill.view.wallet.WalletViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionActivity : BaseActivity() {

    private lateinit var binding: ActivityTransactionBinding
    private val viewModel: TransactionViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up navigation for rectangle_3 click
        binding.rectangle3.setOnClickListener {
            openCategoryFragment()
        }

        binding.rectangle5.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }

        val listPopupWindowButton = binding.rectangle6
        val listPopupWindow = ListPopupWindow(this, null, com.google.android.material.R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView = listPopupWindowButton

        // Observe wallet list changes
        viewModel.walletList.observe(this) { walletDataList ->
            val items = walletDataList.map { it.walletName ?: "Unknown" }
            val adapter = ArrayAdapter(this, R.layout.list_popup_window_wallet, items)
            listPopupWindow.setAdapter(adapter)
        }

        // Set list popup's item click listener
        listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            // Respond to list popup window item click.
            val selectedItem = viewModel.walletList.value?.get(position)?.walletName ?: "Unknown"
            binding.wallet.text = selectedItem
            // Dismiss popup.
            listPopupWindow.dismiss()
        }

        // Show list popup window on button click.
        listPopupWindowButton.setOnClickListener { v: View? -> listPopupWindow.show() }

        // Observe selected category changes
        viewModel.selectedCategory.observe(this, Observer { categoryId ->
            Log.d("TransactionActivity", "Selected category: $categoryId")
            updateUI(categoryId)
        })

        binding.button.setOnClickListener {
            createTransaction()
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_transaction
    }

    private fun createTransaction() {
        val activityType = binding.activityType.text.toString()
        val category = binding.category.text.toString()
        val amount = binding.amountVal.text.toString().toInt()
        val notes = binding.noteVal.text.toString()
        val date = binding.date.text.toString()
        val walletName = binding.wallet.text.toString()
        val walletId = viewModel.walletList.value?.find { it.walletName == walletName }?.walletId ?: ""
        val iconId = 1 // Replace with the actual icon ID based on your app logic
        val billId = 1 // Replace with the actual bill ID based on your app logic

        viewModel.createTransaction(activityType, category, amount, notes, date, walletId, iconId, billId)

        val intent = Intent(this, ListTransactionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("date", formatDate(date))
        startActivity(intent)
    }

    fun handleCategorySelected(categoryId: String, activityType: String) {
        Log.d("TransactionActivity", "Selected category: $categoryId")
        updateUI(categoryId)
        binding.activityType.text = activityType
    }

    fun handleDateSelected(year: Int, month: Int, day: Int) {
        val selectedDate = "$year-${month+1}-$day" // Format the date as needed
        binding.date.text = selectedDate
        Log.d("TransactionActivity", "Selected date: $selectedDate")
    }


    private fun openCategoryFragment() {
        val fragment = CategoryFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateUI(categoryId: String) {
        // Implement your UI update logic based on categoryId
        // For example, update TextView or other UI elements
        binding.category.text = categoryId
    }

    private fun formatDate(originalDate: String): String {
        val originalFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())
        val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = originalFormat.parse(originalDate)
        return targetFormat.format(date)
    }

}