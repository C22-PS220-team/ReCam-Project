package com.dhandev.recam.networking.api

import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.networking.data.response.ResponseTestItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{bahan}")
    fun getRecycleList(
        @Path("bahan") bahan: String
    ): Call<ArrayList<ResponsePaperItem>>
}