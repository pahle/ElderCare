package com.example.capstoneproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.capstoneproject.ui.user.AuthViewModel
import com.example.capstoneproject.ui.user.RegistActivity
import com.example.capstoneproject.databinding.ActivitySplashScreenBinding
import com.example.capstoneproject.utils.ViewModelFactory

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private var isLogin = true
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.getPrefUser().observe(this) {
            isLogin = it.name.isEmpty()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if(isLogin) {
                val intent = Intent(this@SplashScreen, RegistActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 2500)
    }
}