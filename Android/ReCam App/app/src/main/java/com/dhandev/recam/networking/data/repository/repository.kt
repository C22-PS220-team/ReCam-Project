package com.dhandev.recam.networking.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhandev.recam.TokenPreference
import com.dhandev.recam.networking.api.ApiConfig
import com.dhandev.recam.networking.api.ApiService
import com.dhandev.recam.networking.data.LoginInfo
import com.dhandev.recam.networking.data.ResponseLogin
import com.dhandev.recam.ui.login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class repository  constructor(
    private val apiService: ApiService,
    private val preference: TokenPreference
){
    private val _loginData = MutableLiveData<ResponseLogin>()
    val loginData: LiveData<ResponseLogin> = _loginData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email : String, password: String) {
        val body = LoginInfo(email, password)
        val client = ApiConfig.getApiService().loginInfo(body)
        client.enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    _loginData.value = response.body()
                } else {
                    Log.e(LoginViewModel::class.java.simpleName, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e(LoginViewModel::class.java.simpleName, "onFailure: ${t.message.toString()}")
            }

        })
    }
}