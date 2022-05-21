package com.dhandev.recam.networking.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegisterInfo (
    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("email")
    @Expose
    val email: String? = null,

    @SerializedName("password")
    @Expose
    val password: String? = null,
)