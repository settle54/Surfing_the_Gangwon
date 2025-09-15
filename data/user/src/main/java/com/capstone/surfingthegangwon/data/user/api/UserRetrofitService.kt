package com.capstone.surfingthegangwon.data.user.api

import com.capstone.surfingthegangwon.core.retrofit.AuthHeaderInterceptor
import com.capstone.surfingthegangwon.data.user.dto.UserProfileRes
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserRetrofitService {

    @GET("api/users/me")
    @Headers("${AuthHeaderInterceptor.HEADER_REQUIRE_AUTH}: true")
    suspend fun getUserProfile(): UserProfileRes
}