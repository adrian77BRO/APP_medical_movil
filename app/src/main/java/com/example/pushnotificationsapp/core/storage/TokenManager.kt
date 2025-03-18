package com.example.pushnotificationsapp.core.storage

import android.content.Context

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
    companion object {
        private const val KEY_AUTH_TOKEN = "key_auth_token"
    }
    fun setToken(token: String) {
        prefs.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }
    fun getToken(): String? = prefs.getString(KEY_AUTH_TOKEN, null)
}