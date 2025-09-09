package com.capstone.surfingthegangwon.core.auth

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefsStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferenceStorage {
    private val prefs by lazy { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    override var oauthState: String?
        get() = prefs.getString("oauth_state", null)
        set(v) { prefs.edit {
            if (v == null) remove("oauth_state") else putString(
                "oauth_state",
                v
            )
        } }

    override var accessToken: String?
        get() = prefs.getString("access_token", null)
        set(v) { prefs.edit {
            if (v == null) remove("access_token") else putString(
                "access_token",
                v
            )
        } }

    override var refreshToken: String?
        get() = prefs.getString("refresh_token", null)
        set(v) { prefs.edit {
            if (v == null) remove("refresh_token") else putString(
                "refresh_token",
                v
            )
        } }

    override fun clearAll() { prefs.edit { clear() } }

    companion object {
        private const val PREFS_NAME = "surfingthegangwon"
    }
}