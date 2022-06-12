package com.dhandev.recam.ui.result

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.Glide
import com.dhandev.recam.MainActivity
import com.dhandev.recam.R
import com.dhandev.recam.databinding.FragmentResultBinding
import com.dhandev.recam.loadImage
import com.dhandev.recam.networking.data.RecycleAdapter
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.networking.data.response.ResponseTestItem
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import java.util.*

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ResultViewModel
    private lateinit var recycleAdapter: RecycleAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var skeleton: Skeleton

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

        //apply recycleView
        recycleAdapter = RecycleAdapter()
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]
        binding.rvList.adapter = recycleAdapter
        recycleAdapter.setOnItemClickCallBack(object: RecycleAdapter.OnItemClickCallback{
            override fun onItemClicked(user: ResponsePaperItem) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("item", user)
                requireContext().startActivity(intent)
            }

        })
        val result = activity?.intent?.getStringExtra("result")
//        binding.recomend.text = result
        if (result != null) {
            viewModel.setSearchUsers(result)
        }
        skeleton = binding.rvList.applySkeleton(R.layout.item_row_result)
        skeleton.showSkeleton()

        viewModel.getSearchUsers().observe(requireActivity()){
            if (it != null){
                recycleAdapter.setList(it)
                Log.e(requireActivity()::class.java.simpleName,"adapter : $recycleAdapter ")
                skeleton.showOriginal()
            }else{
                Log.e(requireActivity()::class.java.simpleName,"data not found")
            }
        }

        binding.nameOfCreativity.text = getString(R.string.ideas_from, result)
        val bahan = result?.trim()
        when(bahan){
            "kertas" -> loadImage(requireContext(),"https://docs.google.com/uc?id=19G0wAV3F2DVmu70y5GIbDxa9jomX49bu", binding.bahan)
            "kardus" -> loadImage(requireContext(),"https://docs.google.com/uc?id=1eZhC0QeQUsJ74zy9ZV7EsOy6c8CywPcG", binding.bahan)
            "logam" -> loadImage(requireContext(), "https://docs.google.com/uc?id=1nqbgFxaSNfSSV4AhBNo5dRww6_CJWKgX", binding.bahan)
            "plastik" -> loadImage(requireContext(),"https://docs.google.com/uc?id=1qk-uygHDm3f-75VYhvoEWfUZc_5Woaul", binding.bahan)
        }

        return root
    }
//
//    private fun imageGlide(url: String) {
//        Glide.with(requireActivity())
//            .load(url)
//            .placeholder(R.drawable.ic_baseline_image_24)
//            .into(binding.bahan)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}