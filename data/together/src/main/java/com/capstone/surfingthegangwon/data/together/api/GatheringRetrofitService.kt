package com.capstone.surfingthegangwon.data.together.api

import com.capstone.surfingthegangwon.core.retrofit.AuthHeaderInterceptor
import com.capstone.surfingthegangwon.data.together.dto.GatheringRes
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GatheringRetrofitService {

    @GET("api/gathering/{seashore_id}")
    @Headers("${AuthHeaderInterceptor.HEADER_REQUIRE_AUTH}: true")
    suspend fun getGatheringPosts(
        @Path("seashore_id") seashoreId: Int,
        @Query("date") date: String
    ): GatheringRes
}