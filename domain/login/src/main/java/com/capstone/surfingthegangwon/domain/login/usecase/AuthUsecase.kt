package com.capstone.surfingthegangwon.domain.login.usecase

import com.capstone.surfingthegangwon.domain.login.model.TokenPair
import com.capstone.surfingthegangwon.domain.login.repository.AuthRepository

class AuthUsecase (
    private val repo: AuthRepository
) {
    suspend operator fun invoke(code: String): TokenPair = repo.exchangeKakaoCode(code)
}