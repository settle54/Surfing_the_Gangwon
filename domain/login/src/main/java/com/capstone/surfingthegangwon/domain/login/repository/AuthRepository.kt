package com.capstone.surfingthegangwon.domain.login.repository

import com.capstone.surfingthegangwon.domain.login.model.TokenPair

interface AuthRepository {
    suspend fun exchangeKakaoCode(code: String): TokenPair
}