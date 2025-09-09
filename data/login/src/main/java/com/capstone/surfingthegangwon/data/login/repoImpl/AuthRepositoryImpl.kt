package com.capstone.surfingthegangwon.data.login.repoImpl

import android.net.Uri
import android.util.Log
import com.capstone.surfingthegangwon.core.auth.JwtUtils
import com.capstone.surfingthegangwon.core.auth.OAuthConfig
import com.capstone.surfingthegangwon.core.auth.PreferenceStorage
import com.capstone.surfingthegangwon.data.login.api.AuthRetrofitService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthRetrofitService,
    private val prefs: PreferenceStorage
) {
    fun newStateAndAuthorizeUrl(): String {
        val state = java.util.UUID.randomUUID().toString()
        prefs.oauthState = state
        return OAuthConfig.buildAuthorizeUrl(state)
    }

    fun isLoggedInValid(): Boolean {
        val token = prefs.accessToken ?: return false
        return !JwtUtils.isExpired(token)
    }

    suspend fun handleDeepLinkAndLogin(uri: Uri) {
        Log.d("OAUTH", "repo.handleDeepLinkAndLogin uri=$uri")
        val code = uri.getQueryParameter("code") ?: throw IllegalStateException("code 없음")
        val echo = uri.getQueryParameter("state")
        val saved = prefs.oauthState
        if (saved != null && echo != null && saved != echo) throw IllegalStateException("STATE 불일치")
        Log.d("OAUTH", "before exchange: code=$code")

        try {
            val res = api.exchangeCode(code)
            prefs.accessToken = res.accessToken
            prefs.refreshToken = res.refreshToken
            prefs.oauthState = null
        } catch (t: Throwable) {
            Log.e("OAUTH", "exchange failed: ${t.message}", t)
            throw t
        }
    }
}
