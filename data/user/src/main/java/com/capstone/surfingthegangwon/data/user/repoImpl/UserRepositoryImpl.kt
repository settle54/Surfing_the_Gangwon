package com.capstone.surfingthegangwon.data.user.repoImpl

import android.util.Log
import com.capstone.surfingthegangwon.data.user.api.UserRetrofitService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserRetrofitService
) {

    suspend fun getUserProfile(): Result<String> {
        return runCatching {
            val res = userApi.getUserProfile()
            Log.d(TAG, res.userName)
            res.userName
        }
    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}