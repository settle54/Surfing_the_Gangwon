package com.capstone.surfingthegangwon.presentation.sessionReading

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surfingthegangwon.core.model.SessionItem
import com.capstone.surfingthegangwon.data.sessionreading.repoImpl.SessionReadingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SessionReadingViewModel @Inject constructor(
    private val repo: SessionReadingRepositoryImpl
) : ViewModel() {

    private val _session = MutableLiveData<SessionItem>()
    val session: LiveData<SessionItem> = _session

    fun setPost(sessionItem: SessionItem) {
        _session.postValue(sessionItem)
    }

    // 공통 실행기: id가 없으면 조용히 return, 있으면 액션 수행
    private fun runWithSessionId(
        actionName: String,
        block: suspend (Int) -> Result<Unit>
    ) = viewModelScope.launch(Dispatchers.IO) {
        val id = session.value?.id ?: run {
            Log.w(TAG, "[$actionName] session id is null. Ignore.")
            return@launch
        }
        val result = try {
            block(id)
        } catch (e: CancellationException) {
            Log.d(TAG, "[$actionName] 취소됨(viewModelScope cancel)")
            return@launch
        } catch (t: Throwable) {
            Result.failure(t)
        }

        result
            .onSuccess { Log.d(TAG, "[$actionName] 성공") }
            .onFailure { e ->
                if (e is CancellationException) {      // (2) 취소는 무시
                    Log.d(TAG, "[$actionName] 취소됨")
                    return@launch
                }
                Log.e(TAG, "[$actionName] 실패", e)
            }
    }

    fun joinPost() = runWithSessionId("세션 참여") { id -> repo.joinPost(id) }
    fun cancelPost() = runWithSessionId("세션 취소") { id -> repo.cancelPost(id) }
    fun closePost() = runWithSessionId("세션 종료") { id -> repo.closePost(id) }
    fun deletePost() = runWithSessionId("세션 삭제") { id -> repo.deletePost(id) }


    companion object {
        private const val TAG = "SessionReadingViewModel"
    }
}