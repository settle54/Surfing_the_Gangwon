package com.capstone.surfingthegangwon.core.model

import java.io.Serializable

data class SessionItem(
    val id: Int,
    val title: String,
    val contents: String,
    val sessionTime: String,
    val sessionDate: String,
    val participants: String,
    val phoneNumber: String,
    val city: String,
    val seashore: String,
    val grade: Grade,
    val action: PostAction,
    val state: SessionState
) : Serializable
