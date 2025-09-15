package com.capstone.surfingthegangwon.data.city.repoImpl

import com.capstone.surfingthegangwon.data.city.api.CityRetrofitService
import com.capstone.surfingthegangwon.data.city.mapper.toDomainList
import com.capstone.surfingthegangwon.domain.city.City
import com.capstone.surfingthegangwon.domain.city.Seashores
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityApi: CityRetrofitService
) {

    suspend fun getCities(): Result<List<City>> {
        return runCatching {
            val res = cityApi.getCities()
            res.toDomainList()
        }
    }

    suspend fun getSeashores(cityId: Int): Result<List<Seashores>> {
        return runCatching {
            val res = cityApi.getSeashoresBasic(cityId)
            res.toDomainList()
        }
    }
}