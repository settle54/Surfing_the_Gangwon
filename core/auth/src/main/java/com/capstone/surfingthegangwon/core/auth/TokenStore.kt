package com.capstone.surfingthegangwon.core.auth

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun save(access: String, refresh: String) {
        prefs.edit {
            putString("accessToken", access)
                .putString("refreshToken", refresh)
        }
        Log.d(TAG, "token saved.")
    }

    fun getAccessToken(): String? = prefs.getString("accessToken", null)
    fun getRefreshToken(): String? = prefs.getString("refreshToken", null)

    fun clear() {
        prefs.edit { clear() }
    }

    companion object {
        private const val TAG = "TokenStore"
    }
}