package com.dhandev.recam.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dhandev.recam.networking.data.ResponseLogin
import com.dhandev.recam.networking.data.repository.repository

class LoginViewModel constructor(var repository: repository ): ViewModel() {
    val userLogin: LiveData<ResponseLogin> = repository.loginData
    val isLoading: LiveData<Boolean> = repository.isLoading
    fun loginUser(email: String, password: String) = repository.login(email, password)
}