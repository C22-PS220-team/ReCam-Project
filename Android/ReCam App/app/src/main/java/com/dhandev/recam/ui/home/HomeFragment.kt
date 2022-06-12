package com.dhandev.recam.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dhandev.recam.R
import com.dhandev.recam.customTab
import com.dhandev.recam.databinding.FragmentHomeBinding
import com.dhandev.recam.networking.data.ArticleAdapter
import com.dhandev.recam.networking.data.RecycleAdapter
import com.dhandev.recam.networking.data.response.ArticlesItem
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.ui.article.ArticleActivity
import com.dhandev.recam.ui.result.DetailActivity
import com.dhandev.recam.ui.result.ResultViewModel
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.createSkeleton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var skeleton: Skeleton

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val photoUrl = user.photoUrl

            binding.apply {
                Glide.with(this@HomeFragment)
                    .load(photoUrl)
                    .circleCrop()
                    .into(avatar)
                userName.text = name
            }
        }
        //apply recycleView
        articleAdapter = ArticleAdapter()
        binding.article.layoutManager = LinearLayoutManager(requireContext())
        binding.article.adapter = articleAdapter
        articleAdapter.setOnItemClickCallBack(object: ArticleAdapter.OnItemClickCallback{
            override fun onItemClicked(user: ArticlesItem) {
                customTab(requireContext(), user.url)
            }

        })

        homeViewModel.setArticle()
        skeleton = binding.article.applySkeleton(R.layout.item_row_result)
        skeleton.showSkeleton()
        homeViewModel.getArticle().observe(requireActivity()){
            binding.apply {
                val article = it.articles
                if (it != null){
                    articleAdapter.setList(article.slice(0..2))
                    Log.e(requireActivity()::class.java.simpleName,"adapter : $articleAdapter ")
                    skeleton.showOriginal()
                }else{
                    Log.e(requireActivity()::class.java.simpleName,"data not found")
                }

            }
        }

        binding.seeAll.setOnClickListener {
            startActivity(Intent(requireContext(), ArticleActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}