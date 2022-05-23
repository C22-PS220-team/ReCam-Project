package com.dhandev.recam.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.dhandev.recam.MainActivity
import com.dhandev.recam.R
import com.dhandev.recam.databinding.FragmentHomeBinding
import com.dhandev.recam.databinding.FragmentResultBinding
import com.dhandev.recam.ui.camera.ResultCameraActivity

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.arrowBack.setOnClickListener {
            startActivity(Intent(requireContext(), ResultCameraActivity::class.java))
            onStop()
        }
        binding.cardView.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_detailFragment)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}