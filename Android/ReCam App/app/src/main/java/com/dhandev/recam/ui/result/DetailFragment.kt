package com.dhandev.recam.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhandev.recam.R
import com.dhandev.recam.databinding.FragmentDetailBinding
import com.dhandev.recam.databinding.FragmentResultBinding

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.apply {
            arrowBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}