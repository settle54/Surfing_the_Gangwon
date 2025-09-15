package com.capstone.surfingthegangwon.data.together.mapper

import com.capstone.surfingthegangwon.core.model.Grade
import com.capstone.surfingthegangwon.core.model.PostAction
import com.capstone.surfingthegangwon.core.model.SessionItem
import com.capstone.surfingthegangwon.core.model.SessionState
import com.capstone.surfingthegangwon.core.util.formatIsoToKoreanDate
import com.capstone.surfingthegangwon.core.util.toHourMinute
import com.capstone.surfingthegangwon.data.together.dto.GatheringRes
import com.capstone.surfingthegangwon.data.together.dto.GatheringResItem

fun GatheringResItem.toSessionItem(): SessionItem =
    SessionItem(
        id = gatheringId,
        title = title,
        contents = contents,
        sessionTime = meetingTime.toHourMinute(),
        sessionDate = formatIsoToKoreanDate(meetingTime),
        participants = "$currentCount/$maxCount",
        phoneNumber = phone,
        grade = Grade.from(level),
        action = PostAction.from(postAction),
        state = SessionState.from(state),
        city = city,
        seashore = seashore
    )

fun GatheringRes.toSessionItems(): List<SessionItem> = map { it.toSessionItem() }
