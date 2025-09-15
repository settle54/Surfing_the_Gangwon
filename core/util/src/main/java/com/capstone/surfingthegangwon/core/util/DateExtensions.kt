package com.capstone.surfingthegangwon.core.util

import java.time.LocalDateTime

fun String.toHourMinute(): String {
    return try {
        val parsed = LocalDateTime.parse(this) // e.g. "2025-09-03T18:00:00"
        parsed.format(DateUtils.HOUR_MINUTE)    // "18:00"
    } catch (e: Exception) {
        this // 파싱 실패 시 원본 리턴
    }
}

fun formatIsoToKoreanDate(isoString: String): String {
    val ldt = LocalDateTime.parse(isoString)
    return ldt.format(DateUtils.KOREAN_DATE)
}