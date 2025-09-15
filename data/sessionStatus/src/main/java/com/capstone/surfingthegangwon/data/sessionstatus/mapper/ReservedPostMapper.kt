package com.capstone.surfingthegangwon.data.sessionstatus.mapper

import com.capstone.surfingthegangwon.core.model.Grade
import com.capstone.surfingthegangwon.core.model.PostAction
import com.capstone.surfingthegangwon.core.model.SessionItem
import com.capstone.surfingthegangwon.core.model.SessionState
import com.capstone.surfingthegangwon.core.util.formatIsoToKoreanDate
import com.capstone.surfingthegangwon.core.util.toHourMinute
import com.capstone.surfingthegangwon.data.sessionstatus.dto.ReservedPostRes
import com.capstone.surfingthegangwon.data.sessionstatus.dto.ReservedPostResItem

fun ReservedPostResItem.toSessionItem(): SessionItem =
    SessionItem(
        id = id,
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

fun ReservedPostRes.toSessionItems(): List<SessionItem> = map { it.toSessionItem() }