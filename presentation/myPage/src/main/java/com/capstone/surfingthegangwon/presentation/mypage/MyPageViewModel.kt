package com.capstone.surfingthegangwon.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surfingthegangwon.data.user.repoImpl.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val repo: UserRepositoryImpl
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    init {
        getUserProfile()
    }

    private fun getUserProfile() = viewModelScope.launch {
        repo.getUserProfile()
            .onSuccess { name -> _userName.value = name }
            .onFailure { e ->
                Log.e(TAG, "실패", e)
                _userName.value = "" // 에러 처리
            }
    }

    companion object {
        private const val TAG = "MyPageViewModel"
    }

}