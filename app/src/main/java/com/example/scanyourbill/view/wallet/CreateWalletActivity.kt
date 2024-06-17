package com.example.scanyourbill.view.wallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.ActivityCreateWalletBinding
import com.example.scanyourbill.view.ViewModelFactory

class CreateWalletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateWalletBinding
    private val walletViewModel: WalletViewModel by viewModels { ViewModelFactory.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonClickListener()
    }

    private fun setupButtonClickListener() {
        binding.button.setOnClickListener {
            val walletName = binding.accountNameVal.text.toString()
            val amount = binding.amountField.text.toString().toIntOrNull() ?: 0
            val note = binding.notesField.text.toString()
            val iconId = 1 // Hardcoded iconId

            walletViewModel.createWallet(walletName, amount, note, iconId)

            showToast(getString(R.string.success_created_new_wallet))
//            showLoading(false)

            val intent = Intent(this, WalletActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}