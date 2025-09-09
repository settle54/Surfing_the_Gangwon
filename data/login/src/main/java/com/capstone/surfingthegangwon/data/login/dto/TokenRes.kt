package com.capstone.surfingthegangwon.data.login.dto

import com.google.gson.annotations.SerializedName

data class TokenRes(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)


