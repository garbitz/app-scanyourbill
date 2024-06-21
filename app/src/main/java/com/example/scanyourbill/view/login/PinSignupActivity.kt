package com.example.scanyourbill.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.ActivityLoginBinding
import com.example.scanyourbill.databinding.ActivityPinSignupBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.main.MainActivity

class PinSignupActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityPinSignupBinding;

    private lateinit var username: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinSignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.getUsername().observe(this) {
            this.username = it
        }

        val pinField = binding.squareField
        val submitButton = binding.submitBtn

        submitButton.setOnClickListener {

            val pin = pinField.text.toString()
            viewModel.register(username, pin)
        }

        viewModel.registerResult.observe(this) { response ->
            if (response.status == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show()
            }
        }

    }
}