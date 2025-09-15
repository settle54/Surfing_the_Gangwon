package com.capstone.surfingthegangwon.core.retrofit

import com.capstone.surfingthegangwon.core.auth.TokenStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthHeaderInterceptor @Inject constructor(
    private val tokenStore: TokenStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requireAuth = original.header(HEADER_REQUIRE_AUTH)?.equals("true", true) == true

        val newReq = if (requireAuth) {
            val token = tokenStore.getAccessToken()
            val builder = original.newBuilder()
                .removeHeader(HEADER_REQUIRE_AUTH) // 마커 제거

            if (!token.isNullOrEmpty()) {
                builder.addHeader("Authorization", "Bearer $token")
            }
            builder.build()
        } else {
            original
        }
        return chain.proceed(newReq)
    }

    companion object {
        const val HEADER_REQUIRE_AUTH = "X-Requires-Auth"
    }
}
