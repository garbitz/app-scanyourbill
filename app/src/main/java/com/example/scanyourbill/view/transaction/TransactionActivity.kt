package com.example.scanyourbill.view.transaction

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.ActivityTransactionBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.wallet.WalletViewModel

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding
    private val viewModel: TransactionViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up navigation for rectangle_3 click
        val rectangle3 = findViewById<ConstraintLayout>(R.id.rectangle_3)
        rectangle3.setOnClickListener {
            openCategoryFragment()
        }

        // Observe selected category changes
        viewModel.selectedCategory.observe(this, Observer { categoryId ->
            Log.d("TransactionActivity", "Selected category: $categoryId")
            updateUI(categoryId)
        })
    }

    fun handleCategorySelected(categoryId: String) {
        Log.d("TransactionActivity", "Selected category: $categoryId")
        updateUI(categoryId)
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

}