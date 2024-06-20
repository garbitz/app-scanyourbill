package com.example.scanyourbill

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.scanyourbill.databinding.ActivityListTransactionBinding
import com.example.scanyourbill.databinding.ActivityLoginBinding
import com.example.scanyourbill.view.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate

class ListTransactionActivity : AppCompatActivity() {
    companion object {
        @StringRes
        // Define a mutable list of tab titles
        private var TAB_TITLES = mutableListOf<String>()

        // Initial setup of tab titles
        init {
            TAB_TITLES.addAll(listOf(
                "Last Month", "This Month", "Next Month"
            ))
        }

    }

    private lateinit var binding: ActivityListTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.menu.findItem(R.id.transaksi)?.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.transaksi -> {
                    true
                }

                else -> false
            }
        }

        val today = intent.getStringExtra("date") ?: LocalDate.now().toString()
        val dateInfo = getMonthDetails(today)

        val newTitles = listOf(
            dateInfo["lastMonth"]!!,
            dateInfo["thisMonth"]!!,
            dateInfo["nextMonth"]!!
        )


        TAB_TITLES.clear() // Clear existing titles
        TAB_TITLES.addAll(newTitles) // Add new titles

        val sectionsPagerAdapter = TransactionSectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = (TAB_TITLES[position])
        }.attach()


        viewPager.setCurrentItem(1, true)
        supportActionBar?.elevation = 0f
    }

    private fun updateTabTitles(newTitles: List<String>) {


    }


}