package com.capstone.surfingthegangwon.domain.login.model

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)