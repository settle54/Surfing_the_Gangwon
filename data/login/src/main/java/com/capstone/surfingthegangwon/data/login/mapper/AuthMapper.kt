package com.capstone.surfingthegangwon.data.login.mapper

import com.capstone.surfingthegangwon.data.login.dto.TokenRes
import com.capstone.surfingthegangwon.domain.login.model.TokenPair

fun TokenRes.toDomain(): TokenPair =
    TokenPair(accessToken = accessToken, refreshToken = refreshToken)