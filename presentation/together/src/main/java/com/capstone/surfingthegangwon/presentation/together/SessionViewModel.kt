package com.capstone.surfingthegangwon.presentation.together

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surfingthegangwon.core.model.SessionItem
import com.capstone.surfingthegangwon.data.together.repoImpl.GatheringRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val gatheringRepo: GatheringRepositoryImpl
) : ViewModel() {

    private val _sessions = MutableLiveData<List<SessionItem>>()
    val sessions: LiveData<List<SessionItem>> get() = _sessions

    fun getGatheringPosts(seashoreId: Int, date: LocalDate) {
        viewModelScope.launch {
            gatheringRepo.getGatheringPosts(seashoreId, date.toString())
                .onSuccess { sessions -> _sessions.value = sessions }
                .onFailure { e ->
                    Log.e(TAG, "sessions load failed.", e)
                    _sessions.value = emptyList()
                }
        }
    }

    companion object {
        private const val TAG = "SessionViewModel"
    }
}