package com.capstone.surfingthegangwon.core.auth

interface PreferenceStorage {
    var oauthState: String?
    var accessToken: String?
    var refreshToken: String?

    fun clearAll()
}
