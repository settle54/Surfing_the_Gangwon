package com.capstone.surfingthegangwon.presentation.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surfingthegangwon.data.login.repoImpl.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepositoryImpl
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events

    fun initAutoNavigateIfValid() {
        if (repo.isLoggedInValid()) {
            viewModelScope.launch { _events.emit(LoginEvent.NavigateMain) }
        }
    }

    fun onClickKakaoLogin() {
        val url = repo.newStateAndAuthorizeUrl()
        viewModelScope.launch { _events.emit(LoginEvent.OpenKakao(url)) }
    }

    fun onDeepLink(uri: Uri?) {
        Log.d("OAUTH", "onDeepLink 호출됨: $uri")
        if (uri?.scheme != BuildConfig.OAUTH_SCHEME || uri.host != BuildConfig.OAUTH_HOST || uri.path != BuildConfig.OAUTH_PATH) return
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.handleDeepLinkAndLogin(uri)
                _events.emit(LoginEvent.NavigateMain)
            } catch (e: Exception) {
                _events.emit(LoginEvent.ToastMsg("로그인 실패: ${e.message}"))
            } finally {
                _loading.value = false
            }
        }
    }
}

sealed class LoginEvent {
    data class OpenKakao(val url: String): LoginEvent()
    data object NavigateMain: LoginEvent()
    data class ToastMsg(val msg: String): LoginEvent()
}