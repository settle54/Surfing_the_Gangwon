package com.capstone.surfingthegangwon.presentation.sessionstatus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surfingthegangwon.core.model.SessionItem
import com.capstone.surfingthegangwon.data.sessionstatus.mapper.toSessionItems
import com.capstone.surfingthegangwon.data.sessionstatus.repoImpl.PostRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionStatusViewModel @Inject constructor(
    private val repo: PostRepositoryImpl
) : ViewModel() {

    private val _sessions = MutableLiveData<List<SessionItem>>()
    val sessions: LiveData<List<SessionItem>> = _sessions

    fun loadWrittenPosts() = viewModelScope.launch {
        repo.getWrittenPosts()
            .onSuccess { posts -> _sessions.value = posts.toSessionItems() }
            .onFailure { e ->
                Log.e(TAG, "작성 모집글 불러오기 실패", e)
                _sessions.value = emptyList() // 에러 처리
            }
    }

    fun loadReservedPosts() = viewModelScope.launch {
        repo.getReservedPosts()
            .onSuccess { posts -> _sessions.value = posts.toSessionItems() }
            .onFailure { e ->
                Log.e(TAG, "예약 모집글 불러오기 실패", e)
                _sessions.value = emptyList() // 에러 처리
            }
    }

    companion object {
        private const val TAG = "SessionStatusViewModel"
    }

}