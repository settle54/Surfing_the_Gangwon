package com.capstone.surfingthegangwon.core.auth

import java.net.URLEncoder
import java.util.UUID

object OAuthConfig {
    const val CLIENT_ID = BuildConfig.KAKAO_CLIENT_ID
    const val APP_REDIRECT = BuildConfig.OAUTH_APP_DIRECT

    fun buildAuthorizeUrl(state: String): String =
        "https://kauth.kakao.com/oauth/authorize" +
                "?response_type=code" +
                "&client_id=$CLIENT_ID" +
                "&redirect_uri=${URLEncoder.encode(APP_REDIRECT, "UTF-8")}" +
                "&state=$state"

    fun newState(): String = UUID.randomUUID().toString()
}