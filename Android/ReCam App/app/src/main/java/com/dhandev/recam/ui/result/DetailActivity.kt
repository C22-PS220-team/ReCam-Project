package com.dhandev.recam.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dhandev.recam.R
import com.dhandev.recam.databinding.ActivityDetailBinding
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.networking.data.result.StepAdapter

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var stepAdapter: StepAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val namaProduk = intent.getParcelableExtra<ResponsePaperItem>("item") as ResponsePaperItem
        binding.apply {
            arrowBack.setOnClickListener {
                onBackPressed()
            }
            val bahanBahan = namaProduk.isih1.replace("- ", "").splitToSequence("\n").toList()
            val prosedur = namaProduk.isih2.replace("- ", "").splitToSequence("\n").toList()
            nameOfCreativity.text = namaProduk.judul

            Glide.with(this@DetailActivity)
                .load(namaProduk.image)
                .into(imgDetail)
            header1.text = getString(R.string.tools_materials)
//            alatBahan.text = bahanBahan[0]
            header2.text = getString(R.string.procedures)
//            stepByStep.text = namaProduk.isih2


            //apply recycleView Alat Bahan
            stepAdapter = StepAdapter()
            rvAlatBahan.layoutManager = LinearLayoutManager(this@DetailActivity)
            rvAlatBahan.adapter = stepAdapter
            stepAdapter.setList(bahanBahan)

            //apply recycleView prosedur
            stepAdapter = StepAdapter()
            rvProsedur.layoutManager = LinearLayoutManager(this@DetailActivity)
            rvProsedur.adapter = stepAdapter
            stepAdapter.setList(prosedur)
        }

    }
}