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
            val pin = "123456"
            if (username.isNotEmpty()) {
                // Handle login logic here
                Toast.makeText(this, "Username: $username", Toast.LENGTH_SHORT).show()

                viewModel.login(username, pin);


            } else {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loginResult.observe(this, Observer { response ->
            if (response.code == 200) {
                val userLogin = UserModel(
                    response.data?.username ?: "",
                    response.tokens?.access?.token ?: "",
                )

                viewModel.saveSession(userLogin)


                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Anda berhasil login. Mari manage duit yok")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun enableEdgeToEdge() {
        // Implementation for enabling edge-to-edge content
    }
}