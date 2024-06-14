package com.example.scanyourbill

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import com.example.scanyourbill.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


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
                // Handle login logic here
                Toast.makeText(this, "Username: $username", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enableEdgeToEdge() {
        // Implementation for enabling edge-to-edge content
    }
}