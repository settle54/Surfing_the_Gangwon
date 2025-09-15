package com.capstone.surfingthegangwon.data.sessionreading.api

import com.capstone.surfingthegangwon.core.retrofit.AuthHeaderInterceptor
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface SessionReadingRetrofitService {

    @POST("api/gathering/join/{gathering_id}")
    @Headers("${AuthHeaderInterceptor.HEADER_REQUIRE_AUTH}: true")
    suspend fun joinPost(
        @Path("gathering_id") gatheringId: Int
    ): Unit

    @POST("api/gathering/close/{gathering_id}")
    @Headers("${AuthHeaderInterceptor.HEADER_REQUIRE_AUTH}: true")
    suspend fun closePost(
        @Path("gathering_id") gatheringId: Int
    ): Unit

    @POST("api/gathering/cancel/{gathering_id}")
    @Headers("${AuthHeaderInterceptor.HEADER_REQUIRE_AUTH}: true")
    suspend fun cancelPost(
        @Path("gathering_id") gatheringId: Int
    ): Unit

    @DELETE("api/gathering/{gathering_id}")
    @Headers("${AuthHeaderInterceptor.HEADER_REQUIRE_AUTH}: true")
    suspend fun deletePost(
        @Path("gathering_id") gatheringId: Int
    )
}