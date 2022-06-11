package com.dhandev.recam.ui.result

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.recam.R
import com.dhandev.recam.databinding.FragmentResultBinding
import com.dhandev.recam.networking.data.RecycleAdapter

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ResultViewModel
    private lateinit var recycleAdapter: RecycleAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.arrowBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.rvList.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_detailFragment)
        }
        sharedPreferences = requireActivity().getSharedPreferences("KLASIFIKASI",
            AppCompatActivity.MODE_PRIVATE
        )
        val klasifikasi = sharedPreferences.getString("RESULT_DETECT", "kertas")
        recycleAdapter = RecycleAdapter()
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]
        binding.rvList.adapter = recycleAdapter
        viewModel.setSearchUsers()
        viewModel.getSearchUsers().observe(requireActivity()){
            if (it != null){
                recycleAdapter.setList(it)
                Log.e(requireActivity()::class.java.simpleName,"adapter : $recycleAdapter ")
            }else{
                Log.e(requireActivity()::class.java.simpleName,"nggak ada isinya ")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}