package com.capstone.surfingthegangwon.data.login.dto

import com.google.gson.annotations.SerializedName

data class TokenReq(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)