package com.capstone.surfingthegangwon.data.login.datasource

import com.capstone.surfingthegangwon.data.login.api.AuthRetrofitService
import javax.inject.Inject

class ServerAuthDataSource @Inject constructor(
    private val authApi: AuthRetrofitService
) {

//    private fun exchangeCodeWithBackend(code: String) {
//        try {
//            val res = authApi.exchangeKakaoCode(CodeReq(code))
//            // TODO: 토큰 저장(SharedPreferences/DataStore)
//            Log.i("Kakao", "로그인 성공! appToken=${res.accessToken}")
//            // 다음 화면 이동 등 앱 로직 진행
//        } catch (e: Exception) {
//            Log.e("Kakao", "백엔드 교환 실패", e)
//        }
//    }
}