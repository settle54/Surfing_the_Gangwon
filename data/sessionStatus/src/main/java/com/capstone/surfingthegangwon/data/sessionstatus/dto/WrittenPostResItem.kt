package com.capstone.surfingthegangwon.data.sessionstatus.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WrittenPostResItem(
    @SerialName("contents")
    val contents: String,
    @SerialName("currentCount")
    val currentCount: Int,
    @SerialName("date")
    val date: String,
    @SerialName("id")
    val id: Int,
    @SerialName("level")
    val level: String,
    @SerialName("maxCount")
    val maxCount: Int,
    @SerialName("meetingTime")
    val meetingTime: String,
    @SerialName("city")
    val city: String,
    @SerialName("seashore")
    val seashore: String,
    @SerialName("postAction")
    val postAction: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("state")
    val state: String,
    @SerialName("title")
    val title: String
)