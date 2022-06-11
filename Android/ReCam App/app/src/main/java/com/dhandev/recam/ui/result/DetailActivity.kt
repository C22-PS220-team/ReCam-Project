package com.dhandev.recam.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dhandev.recam.databinding.ActivityDetailBinding
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.networking.data.response.ResponseTestItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val namaProduk = intent.getParcelableExtra<ResponsePaperItem>("item") as ResponsePaperItem
        binding.apply {
            arrowBack.setOnClickListener {
                onBackPressed()
            }
            nameOfCreativity.text = namaProduk.judul
            Glide.with(this@DetailActivity)
                .load(namaProduk.image)
                .into(imgDetail)
            header1.text = namaProduk.header1
            alatBahan.text = namaProduk.isih1
            header2.text = namaProduk.header2
            stepByStep.text = namaProduk.isih2
        }
    }
}