package com.example.scanyourbill.view.transaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}