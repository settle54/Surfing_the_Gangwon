package com.capstone.surfingthegangwon.core.auth

object JwtUtils {
    /** JWT payload.exp(초) 만료 여부. 실패/없음 = 만료로 간주 */
    fun isExpired(jwt: String): Boolean {
        return try {
            val payload = jwt.split(".").getOrNull(1) ?: return true
            val json = String(
                android.util.Base64.decode(payload, android.util.Base64.URL_SAFE),
                Charsets.UTF_8
            )
            val exp = org.json.JSONObject(json).optLong("exp", 0L)
            exp == 0L || (System.currentTimeMillis() / 1000) >= exp
        } catch (_: Exception) {
            true
        }
    }
}