package com.capstone.surfingthegangwon.data.login.repoImpl

import android.util.Log
import com.capstone.surfingthegangwon.core.auth.TokenStore
import com.capstone.surfingthegangwon.data.login.api.AuthRetrofitService
import com.capstone.surfingthegangwon.data.login.dto.TokenReq
import com.capstone.surfingthegangwon.domain.login.model.TokenPair
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthRetrofitService,
    private val tokenStore: TokenStore
) {

    fun cacheKakaoTokens(access: String, refresh: String) {
        tokenStore.save(access, refresh)
    }

    // 서버로 교환 요청 (201 빈 바디 가정)
    suspend fun exchangeCode(tokenPair: TokenPair): Result<Unit> {
        return runCatching {
            val req = TokenReq(
                accessToken = tokenPair.accessToken,
                refreshToken = tokenPair.refreshToken
            )
            val res = authApi.exchangeCode(req)

            if (res.isSuccessful) {
                Unit
            } else {
                val body = res.errorBody()?.string()
                throw HttpException(res)
                    .also { Log.e(TAG, "교환 실패: code=${res.code()}, body=$body") }
            }
        }
    }

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
}
