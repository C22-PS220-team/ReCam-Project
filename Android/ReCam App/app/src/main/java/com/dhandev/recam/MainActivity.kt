package com.dhandev.recam

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dhandev.recam.databinding.ActivityMainBinding
import com.dhandev.recam.ui.camera.ResultCameraActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPred : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNav

        loadLang()

        binding.apply {
            navView.background = null
            navView.menu.getItem(1).isEnabled = false
            fabCamera.setOnClickListener {
                startActivity(Intent(this@MainActivity, ResultCameraActivity::class.java))
            }
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            if (destination.id == R.id.splashFragment){
                navView.visibility = View.GONE
                binding.bottomNav.visibility = View.GONE
                binding.fabCamera.visibility = View.GONE
                binding.coordinatorLayout.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
                binding.bottomNav.visibility = View.VISIBLE
                binding.fabCamera.visibility = View.VISIBLE
                binding.coordinatorLayout.visibility = View.VISIBLE
            }
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)
    }

    private fun loadLang() {
        sharedPred = this.getSharedPreferences("User", MODE_PRIVATE)
        val language = sharedPred.getString("Bahasa", "en")

        val config = resources.configuration
        val locale = Locale(language)

        Locale.setDefault(locale)
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}