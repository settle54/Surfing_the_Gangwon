package com.capstone.surfingthegangwon.domain.together.model

import com.capstone.surfingthegangwon.core.model.Grade

sealed class SessionListItem {
    data class Header(
        val beachName: String,
        val temperature: String,
        val wave: String,
        val wind: String
    ) : SessionListItem()

    data class Content(
        val title: String,
        val sessionTime: String,
        val time: String,
        val participants: String,
        val grade: Grade
    ) : SessionListItem()
}
