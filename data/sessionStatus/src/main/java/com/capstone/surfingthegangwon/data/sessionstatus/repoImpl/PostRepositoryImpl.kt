package com.capstone.surfingthegangwon.data.sessionstatus.repoImpl

import com.capstone.surfingthegangwon.data.sessionstatus.api.PostRetrofitService
import com.capstone.surfingthegangwon.data.sessionstatus.dto.ReservedPostRes
import com.capstone.surfingthegangwon.data.sessionstatus.dto.WrittenPostRes
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostRetrofitService
) {

    suspend fun getWrittenPosts(): Result<WrittenPostRes> {
        return runCatching {
            postApi.getWrittenPosts()
        }
    }

    suspend fun getReservedPosts(): Result<ReservedPostRes> {
        return runCatching {
            postApi.getReservedPosts()
        }
    }
}