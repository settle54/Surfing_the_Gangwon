package com.capstone.surfingthegangwon.data.sessionreading.repoImpl

import android.util.Log
import com.capstone.surfingthegangwon.data.sessionreading.api.SessionReadingRetrofitService
import retrofit2.HttpException
import javax.inject.Inject

class SessionReadingRepositoryImpl @Inject constructor(
    private val readingApi: SessionReadingRetrofitService
) {

    suspend fun joinPost(gatheringId: Int): Result<Unit> {
        return runCatching {
            val res = readingApi.joinPost(gatheringId)
            Log.d(TAG, "$res")
        }
    }

    suspend fun closePost(gatheringId: Int): Result<Unit> {
        return runCatching {
            readingApi.closePost(gatheringId)
        }.onFailure { e ->
            if (e is HttpException) {
                val code = e.code()
                val msg = e.message()
                val err = e.response()?.errorBody()?.string()
                Log.e(TAG, "closePost HTTP $code: $msg\nerrorBody=$err")
            }
        }
    }

    suspend fun cancelPost(gatheringId: Int): Result<Unit> {
        return runCatching {
            readingApi.cancelPost(gatheringId)
        }
    }

    suspend fun deletePost(gatheringId: Int): Result<Unit> {
        return runCatching {
            readingApi.deletePost(gatheringId)
        }.onFailure { e ->
            if (e is HttpException) {
                val code = e.code()
                val msg = e.message()
                val err = e.response()?.errorBody()?.string()
                Log.e(TAG, "closePost HTTP $code: $msg\nerrorBody=$err")
            }
        }
    }

    companion object {
        private const val TAG = "SessionReadingRepositoryImpl"
    }
}