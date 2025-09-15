package com.capstone.surfingthegangwon.core.util

import java.time.format.DateTimeFormatter
import java.util.Locale

class DateUtils {
    companion object {
        val KOREAN_DATE: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREAN)

        val HOUR_MINUTE: DateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN)
    }
}
