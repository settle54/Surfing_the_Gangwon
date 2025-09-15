package com.capstone.surfingthegangwon.data.city.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeashoresBasicResItem(
    @SerialName("beachCode")
    val beachCode: Int,
    @SerialName("name")
    val name: String,
    @SerialName("seashoreId")
    val seashoreId: Int
)