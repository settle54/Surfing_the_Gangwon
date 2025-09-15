package com.capstone.surfingthegangwon.core.util

import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatters {
    val koreanDate: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREAN)
    val hourMinute: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN)
}