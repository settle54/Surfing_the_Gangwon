package com.capstone.surfingthegangwon.data.city.api

import com.capstone.surfingthegangwon.data.city.dto.CityRes
import com.capstone.surfingthegangwon.data.city.dto.SeashoresBasicRes
import retrofit2.http.GET
import retrofit2.http.Path

interface CityRetrofitService {

    @GET("api/cities")
    suspend fun getCities(): CityRes

    @GET("api/cities/{city_id}/seashores/basic")
    suspend fun getSeashoresBasic(
        @Path("city_id") cityId: Int
    ): SeashoresBasicRes
}