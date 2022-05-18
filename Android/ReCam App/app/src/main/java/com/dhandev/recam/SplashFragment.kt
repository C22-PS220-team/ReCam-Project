package com.dhandev.recam

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dhandev.recam.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding : FragmentSplashBinding? = null
    private val binding get() =_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_navigation_home)
        }, 2000)

        val animation : ObjectAnimator = ObjectAnimator.ofInt(binding.progressCircular, "progress", 0, 110)
        animation.setDuration(1900)
        animation.interpolator
        animation.start()
        val appName = getString(R.string.app_name)
        binding.version.text ="$appName \nVersi ${BuildConfig.VERSION_NAME}"
        return root
    }
}