package com.example.scanyourbill.view.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }
}