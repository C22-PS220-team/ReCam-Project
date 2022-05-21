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
        }, 4000)
    }
    private fun version(){
        val versi = getString(R.string.version)
        binding.version.text ="$versi ${BuildConfig.VERSION_NAME}"
    }
    private fun animation(){
        ObjectAnimator.ofFloat(binding.logogram, View.ALPHA, -10f, 10f).apply {
            duration = 1000
        }.start()

        val version = ObjectAnimator.ofFloat(binding.version, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(version)
            start()
        }
    }
}