package com.capstone.surfingthegangwon.data.user.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRes(
    @SerialName("userName")
    val userName: String
)