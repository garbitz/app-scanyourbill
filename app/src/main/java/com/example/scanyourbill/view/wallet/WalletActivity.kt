package com.example.scanyourbill.view.wallet

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanyourbill.R
import com.example.scanyourbill.data.ApiService
import com.example.scanyourbill.data.UserPreference
import com.example.scanyourbill.data.repository.WalletRepository
import com.example.scanyourbill.databinding.ActivityWalletBinding
import com.example.scanyourbill.view.BaseActivity
import com.example.scanyourbill.view.ViewModelFactory

class WalletActivity : BaseActivity() {

    private lateinit var binding: ActivityWalletBinding

    // Use viewModels delegate to get ViewModel with factory
    private val walletViewModel: WalletViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }
    private lateinit var walletAdapter: WalletAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView and Adapter
        walletAdapter = WalletAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = walletAdapter

        val button = binding.button
        button.setOnClickListener{
            val intent = Intent(this, CreateWalletActivity::class.java)
            startActivity(intent)
        }

        // Observe LiveData from ViewModel
        walletViewModel.wallets.observe(this, Observer { walletList ->
            walletAdapter.setWalletList(walletList)
        })

        // Fetch wallets
        walletViewModel.fetchWallets()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_wallet
    }
}