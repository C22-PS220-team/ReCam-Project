package com.dhandev.recam

import android.content.Context

class TokenPreference (context: Context) {
    companion object {
        private const val PREFS_NAME = "token_prefs"
        private const val TOKEN = "token"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getToken(): String? {
        return preference.getString(TOKEN, "")
    }
}