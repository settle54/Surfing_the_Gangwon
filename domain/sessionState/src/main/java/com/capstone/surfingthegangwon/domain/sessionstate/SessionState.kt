package com.capstone.surfingthegangwon.domain.sessionstate

sealed class SessionState(val label: String) {
    data object OPEN : SessionState("open")
    data object CLOSE : SessionState("close")

    companion object {
        fun from(value: String): SessionState = when (value.lowercase()) {
            "open" -> OPEN
            "close" -> CLOSE
            else -> OPEN
        }
    }
}
