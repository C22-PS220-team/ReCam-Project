package com.dhandev.recam.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhandev.recam.networking.api.ApiConfig
import com.dhandev.recam.networking.data.response.ArticlesItem
import com.dhandev.recam.networking.data.response.ResponseArticle
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    val listUser = MutableLiveData<ResponseArticle>()
    fun setArticle() {
        ApiConfig.getApiServiceArticle()
            .getArticle()
            .enqueue(object : Callback<ResponseArticle> {
                override fun onResponse(
                    call: Call<ResponseArticle>,
                    response: Response<ResponseArticle>
                ) {
                    listUser.postValue(response.body())
                }
                override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })

    }

    fun getArticle(): LiveData<ResponseArticle> {
        return listUser
    }
}