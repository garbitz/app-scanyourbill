package com.example.scanyourbill.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.scanyourbill.ListTransactionActivity
import com.example.scanyourbill.R
import com.example.scanyourbill.view.main.MainActivity
import com.example.scanyourbill.view.wallet.WalletActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        setupBottomNavigation()
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            Log.d("BaseActivity", "Item selected: ${menuItem.itemId}")
            when (menuItem.itemId) {
                R.id.home -> {
                    // Handle navigation_home click
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    intent = Intent(this@BaseActivity, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.transaksi -> {
                    // Handle navigation_dashboard click
                    intent = Intent(this@BaseActivity, ListTransactionActivity::class.java)
                    intent.putExtra("date", LocalDate.now().toString())
                    startActivity(intent)
                    true
                }
                R.id.wallet -> {
                    // Handle navigation_notifications click
                    intent = Intent(this, WalletActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.account -> {
                    // Handle navigation_slideshow click
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.scan -> {
                    // Handle navigation_wallet click
                    true
                }
                else -> false
            }
        }
    }
}
