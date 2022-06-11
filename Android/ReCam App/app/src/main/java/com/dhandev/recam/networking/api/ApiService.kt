package com.dhandev.recam.networking.api

import com.dhandev.recam.networking.data.response.ResponsePaperItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("kertas")
    fun getRecycleList(): Call<ArrayList<ResponsePaperItem>>

}