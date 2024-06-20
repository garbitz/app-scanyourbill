package com.example.scanyourbill.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import com.example.scanyourbill.R
import com.example.scanyourbill.databinding.ActivityPinLoginBinding
import com.example.scanyourbill.view.ViewModelFactory
import com.example.scanyourbill.view.main.MainActivity

class PinLoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityPinLoginBinding

    private lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen() // Show the splash screen
        super.onCreate(savedInstanceState)

        binding = ActivityPinLoginBinding.inflate(layoutInflater)

//        installSplashScreen()
        setContentView(binding.root)

        viewModel.getUsername().observe(this) { username ->
            this.username = username
        }

        val pinField = binding.circleField

        pinField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // No-op
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No-op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 6) {
                    viewModel.login(username, s.toString())

                }
            }
        })

        viewModel.getSession().observe(this) { user ->
            if (user.username == "") {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.loginResult.observe(this) { response ->
            if (response.code == 200) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()

            } else if (response.code == 400) {
                Toast.makeText(this, "Username atau password salah, silahkan coba lagi", Toast.LENGTH_SHORT).show()
                pinField.setText("")
            } else {
                Toast.makeText(this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show()
                pinField.setText("")
            }
        }

    }
}