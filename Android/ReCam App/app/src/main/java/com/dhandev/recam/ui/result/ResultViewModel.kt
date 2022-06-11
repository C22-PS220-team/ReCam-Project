package com.dhandev.recam.ui.result

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhandev.recam.networking.api.ApiConfig
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel : ViewModel(){
    val listUser = MutableLiveData<ArrayList<ResponsePaperItem>>()
    private lateinit var sharedPreferences: SharedPreferences

    fun setSearchUsers() {
//        when(bahan){
//            "kertas" -> {
//
//            }
//        }
        ApiConfig.getApiService()
            .getRecycleList()
            .enqueue(object : Callback<ArrayList<ResponsePaperItem>> {
                override fun onResponse(
                    call: Call<ArrayList<ResponsePaperItem>>,
                    response: Response<ArrayList<ResponsePaperItem>>
                ) {
                    listUser.postValue(response.body())
                }
                override fun onFailure(call: Call<ArrayList<ResponsePaperItem>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })

    }

    fun getSearchUsers(): LiveData<ArrayList<ResponsePaperItem>> {
        return listUser
    }
}