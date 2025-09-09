package com.capstone.surfingthegangwon.data.login.api

import com.capstone.surfingthegangwon.data.login.dto.TokenRes
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRetrofitService {

    @POST("/kakao/callback")
    suspend fun exchangeCode(@Query("code") code: String): TokenRes
}