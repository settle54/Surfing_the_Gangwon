package com.capstone.surfingthegangwon.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surfingthegangwon.data.login.repoImpl.AuthRepositoryImpl
import com.capstone.surfingthegangwon.domain.login.model.TokenPair
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepositoryImpl
) : ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> get() = _loginState

    fun exchangeCode(token: TokenPair) {
        viewModelScope.launch {
            try {
                //  먼저 카카오 토큰을 로컬에 저장
                repo.cacheKakaoTokens(token.accessToken, token.refreshToken)

                // 서버 교환 (201 빈 바디 예상)
                repo.exchangeCode(token)
                    .onSuccess {
                        Log.i(TAG, "서비스 토큰 교환 성공")
                        _loginState.postValue(true)
                    }
                    .onFailure { e ->
                        Log.e(TAG, "백엔드 토큰 교환 실패", e)
                        _loginState.postValue(false)
                    }
            } catch (t: Throwable) {
                Log.e(TAG, "예외", t)
                _loginState.postValue(false)
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}