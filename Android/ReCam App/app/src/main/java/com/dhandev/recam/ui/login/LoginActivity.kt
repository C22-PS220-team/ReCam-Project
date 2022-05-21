package com.dhandev.recam.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dhandev.recam.BuildConfig
import com.dhandev.recam.MainActivity
import com.dhandev.recam.R
import com.dhandev.recam.databinding.ActivityLoginBinding
import com.dhandev.recam.ui.home.HomeFragment
import com.dhandev.recam.ui.register.RegisterActivity
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var sharedPred : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadLang()
        verification()
        login()
        register()
        setupView()
    }
    private fun verification(){

    }
    private fun register(){
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
    private fun login(){
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    private fun loadLang() {
        sharedPred = this.getSharedPreferences("User", MODE_PRIVATE)
        val language = sharedPred.getString("Bahasa", "en")

        val config = resources.configuration
        val locale = Locale(language)

        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}