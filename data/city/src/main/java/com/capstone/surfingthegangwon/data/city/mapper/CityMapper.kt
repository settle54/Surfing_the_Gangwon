package com.capstone.surfingthegangwon.data.city.mapper

import com.capstone.surfingthegangwon.data.city.dto.CityRes
import com.capstone.surfingthegangwon.data.city.dto.CityResItem
import com.capstone.surfingthegangwon.domain.city.City

// 단일 아이템 매핑
fun CityResItem.toDomain(): City =
    City(
        areaCode = areaCode,
        cityId = cityId,
        cityName = cityName
    )

// 리스트 매핑
fun CityRes.toDomainList(): List<City> =
    this.map { it.toDomain() }
