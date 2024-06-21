package com.example.scanyourbill.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.scanyourbill.data.UserModel
import com.example.scanyourbill.view.main.MainActivity
import com.example.scanyourbill.databinding.ActivityLoginBinding
import com.example.scanyourbill.view.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding;
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val usernameField = binding.usernameEditText
        val submitButton = binding.submitBtn

        submitButton.setOnClickListener {
            val username = usernameField.text.toString()
            if (username.isNotEmpty()) {

                // go into pinsignup activity and put extra username
                viewModel.saveUsername(username)

                val intent = Intent(this, PinSignupActivity::class.java)
                startActivity(intent)


            } else {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun enableEdgeToEdge() {
        // Implementation for enabling edge-to-edge content
    }
}