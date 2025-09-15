package com.capstone.surfingthegangwon.data.together.repoImpl

import com.capstone.surfingthegangwon.core.model.SessionItem
import com.capstone.surfingthegangwon.data.together.api.GatheringRetrofitService
import com.capstone.surfingthegangwon.data.together.mapper.toSessionItems
import javax.inject.Inject

class GatheringRepositoryImpl @Inject constructor(
    private val gatheringApi: GatheringRetrofitService
) {

    suspend fun getGatheringPosts(
        seashoreId: Int,
        date: String
    ): Result<List<SessionItem>> {
        return runCatching {
            val res = gatheringApi.getGatheringPosts(
                seashoreId = seashoreId,
                date = date
            )
            res.toSessionItems()
        }
    }
}