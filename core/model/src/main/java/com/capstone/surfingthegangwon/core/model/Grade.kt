package com.capstone.surfingthegangwon.core.model

sealed class Grade(val label: String) {
    data object Beginner : Grade("초급")
    data object Intermediate : Grade("중급")
    data object Advanced : Grade("고급")

    companion object {
        fun from(label: String): Grade = when(label) {
            "초급" -> Beginner
            "중급" -> Intermediate
            "고급" -> Advanced
            else -> Beginner
        }
    }
}
