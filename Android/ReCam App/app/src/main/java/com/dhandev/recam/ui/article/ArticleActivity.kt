package com.dhandev.recam.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dhandev.recam.R
import com.dhandev.recam.customTab
import com.dhandev.recam.databinding.ActivityArticleBinding
import com.dhandev.recam.networking.data.ArticleAdapter
import com.dhandev.recam.networking.data.response.ArticlesItem
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.ui.home.HomeViewModel
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var skeleton: Skeleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.apply {
            arrowBack.setOnClickListener {
                onBackPressed()
            }
            //apply recycleView
            articleAdapter = ArticleAdapter()
            binding.rvArticle.layoutManager = LinearLayoutManager(this@ArticleActivity)
            binding.rvArticle.adapter = articleAdapter
            articleAdapter.setOnItemClickCallBack(object: ArticleAdapter.OnItemClickCallback{
                override fun onItemClicked(user: ArticlesItem) {
                    customTab(this@ArticleActivity, user.url)
                }

            })

            homeViewModel.setArticle()
            skeleton = binding.rvArticle.applySkeleton(R.layout.item_row_result)
            skeleton.showSkeleton()
            homeViewModel.getArticle().observe(this@ArticleActivity){
                binding.apply {
                    val article = it.articles
                    if (it != null){
                        articleAdapter.setList(article)
                        Log.e(this@ArticleActivity::class.java.simpleName,"adapter : $articleAdapter ")
                        skeleton.showOriginal()
                    }else{
                        Log.e(this@ArticleActivity::class.java.simpleName,"data not found")
                    }

                }
            }
        }
    }
}