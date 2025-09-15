package com.capstone.surfingthegangwon.data.login.api

import com.capstone.surfingthegangwon.data.login.dto.TokenReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitService {

    @POST("auth/kakao/register")
    suspend fun exchangeCode(
        @Body tokenReq: TokenReq
    ): Response<Unit>
}