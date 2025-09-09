package com.capstone.surfingthegangwon.domain.sessionstate

import com.capstone.surfingthegangwon.core.model.Grade

data class SessionItem(
    val title: String,
    val sessionTime: String,
    val time: String,
    val participants: String,
    val grade: Grade,
    val state: SessionState
)
