package com.capstone.surfingthegangwon.data.together.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GatheringResItem(
    @SerialName("contents")
    val contents: String,
    @SerialName("currentCount")
    val currentCount: Int,
    @SerialName("date")
    val date: String,
    @SerialName("gatheringId")
    val gatheringId: Int,
    @SerialName("level")
    val level: String,
    @SerialName("maxCount")
    val maxCount: Int,
    @SerialName("city")
    val city: String,
    @SerialName("seashore")
    val seashore: String,
    @SerialName("meetingTime")
    val meetingTime: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("postAction")
    val postAction: String,
    @SerialName("state")
    val state: String,
    @SerialName("title")
    val title: String
)