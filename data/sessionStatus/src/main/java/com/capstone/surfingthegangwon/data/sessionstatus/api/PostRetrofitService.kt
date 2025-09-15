package com.capstone.surfingthegangwon.data.sessionstatus.api

import com.capstone.surfingthegangwon.core.retrofit.AuthHeaderInterceptor
import com.capstone.surfingthegangwon.data.sessionstatus.dto.ReservedPostRes
import com.capstone.surfingthegangwon.data.sessionstatus.dto.WrittenPostRes
import retrofit2.http.GET
import retrofit2.http.Headers

interface PostRetrofitService {

    @GET("api/users/written-posts")
    @Headers("${AuthHeaderInterceptor.HEADER_REQUIRE_AUTH}: true")
    suspend fun getWrittenPosts(): WrittenPostRes

    @GET("api/users/reserved-post")
    @Headers("${AuthHeaderInterceptor.HEADER_REQUIRE_AUTH}: true")
    suspend fun getReservedPosts(): ReservedPostRes
}