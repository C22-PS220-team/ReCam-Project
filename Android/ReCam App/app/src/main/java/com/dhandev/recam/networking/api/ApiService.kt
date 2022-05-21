package com.dhandev.recam.networking.api

import com.dhandev.recam.networking.data.LoginInfo
import com.dhandev.recam.networking.data.RegisterInfo
import com.dhandev.recam.networking.data.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    fun createUser(@Body registerInfo: RegisterInfo) : Call<ResponseLogin>

    @POST("login")
    fun loginInfo(@Body loginInfo: LoginInfo) : Call<ResponseLogin>

}