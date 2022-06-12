package com.dhandev.recam.networking.api

import com.dhandev.recam.networking.data.response.ArticlesItem
import com.dhandev.recam.networking.data.response.ResponseArticle
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.networking.data.response.ResponseTestItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{bahan}")
    fun getRecycleList(
        @Path("bahan") bahan: String
    ): Call<ArrayList<ResponsePaperItem>>

    @GET("everything?q=\"climate change\"")
    @Headers("Authorization: 8ee90041124949c6af6e5df48e3e5f23")
    fun getArticle(
    ): Call<ResponseArticle>
}