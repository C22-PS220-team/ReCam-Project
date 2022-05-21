package com.dhandev.recam.ui.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.dhandev.recam.BuildConfig
import com.dhandev.recam.R
import com.dhandev.recam.databinding.ActivityRegisterBinding
import com.dhandev.recam.ui.login.LoginActivity
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var sharedPred : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        finishedRegister()
        loadLang()
    }
    private fun finishedRegister(){
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
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
}