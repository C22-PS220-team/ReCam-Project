package com.dhandev.recam.ui.result

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.recam.MainActivity
import com.dhandev.recam.R
import com.dhandev.recam.databinding.FragmentResultBinding
import com.dhandev.recam.networking.data.RecycleAdapter
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.networking.data.response.ResponseTestItem

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
        sharedPreferences = requireActivity().getSharedPreferences("KLASIFIKASI",
            AppCompatActivity.MODE_PRIVATE
        )
        val klasifikasi = sharedPreferences.getString("RESULT_DETECT", "test")
        recycleAdapter = RecycleAdapter()
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]
        binding.rvList.adapter = recycleAdapter
        recycleAdapter.setOnItemClickCallBack(object: RecycleAdapter.OnItemClickCallback{
            override fun onItemClicked(user: ResponsePaperItem) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("item", user)
                requireContext().startActivity(intent)
                Toast.makeText(requireContext(), "thiss", Toast.LENGTH_SHORT).show()
            }

        })
        val result = activity?.intent?.getStringExtra("result")
//        binding.recomend.text = result
        if (result != null) {
            viewModel.setSearchUsers(result)
        }
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