package com.capstone.surfingthegangwon.core.model

sealed class PostAction {
    data object Join : PostAction()
    data object Cancel : PostAction()
    data object Complete : PostAction()

    companion object {
        fun from(label: String): PostAction = when (label) {
            "JOIN" -> Join
            "CANCEL" -> Cancel
            "COMPLETE" -> Complete
            else -> Join
        }
    }
}
