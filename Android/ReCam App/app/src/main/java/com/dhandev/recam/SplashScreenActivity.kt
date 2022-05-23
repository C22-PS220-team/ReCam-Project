package com.dhandev.recam

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.graphics.translationMatrix
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dhandev.recam.databinding.ActivityLoginBinding
import com.dhandev.recam.databinding.ActivitySplashScreenBinding
import com.dhandev.recam.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verification()
        version()
        animation()
    }
    private fun verification(){
        Handler(Looper.myLooper()!!).postDelayed({
            val token = TokenPreference(this)
            when{
                token.getToken().isNullOrBlank() -> startActivity(Intent(this,LoginActivity::class.java))
                else -> startActivity(Intent(this,MainActivity::class.java))
            }
            finish()
        }, 2000)
    }
    private fun version(){
        val versi = getString(R.string.version)
        binding.version.text ="$versi ${BuildConfig.VERSION_NAME}"
    }
    private fun animation(){
        val animation : ObjectAnimator = ObjectAnimator.ofInt(binding.progressBar, "progress", 0, 110)
        animation.duration = 1900
        animation.interpolator
        animation.start()
    }
}