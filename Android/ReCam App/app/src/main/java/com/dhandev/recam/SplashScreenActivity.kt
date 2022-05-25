package com.dhandev.recam

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.translationMatrix
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dhandev.recam.databinding.ActivityLoginBinding
import com.dhandev.recam.databinding.ActivitySplashScreenBinding
import com.dhandev.recam.ui.login.LoginActivity
import com.dhandev.recam.ui.settings.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animation()
        verification()
        version()

        val pref = SettingPreferences.getInstance(this.dataStore)
        val settingsViewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(SettingsViewModel::class.java)

        settingsViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
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
        animation.duration = 2000
        animation.interpolator
        animation.start()
        return
    }
}