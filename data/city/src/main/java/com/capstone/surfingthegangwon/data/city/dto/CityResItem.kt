package com.capstone.surfingthegangwon.data.city.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResItem(
    @SerialName("areaCode")
    val areaCode: String,
    @SerialName("cityId")
    val cityId: Int,
    @SerialName("cityName")
    val cityName: String
)