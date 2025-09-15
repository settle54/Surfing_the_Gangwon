package com.capstone.surfingthegangwon.presentation.together

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.surfingthegangwon.data.city.repoImpl.CityRepositoryImpl
import com.capstone.surfingthegangwon.domain.city.Seashores
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeashoreViewModel @Inject constructor(
    private val cityRepo: CityRepositoryImpl
) : ViewModel() {

    private val _seashores = MutableLiveData<List<Seashores>>()
    val seashores: LiveData<List<Seashores>> get() = _seashores

    init {
        getSeashores(1)
    }

    fun getSeashores(cityId: Int) {
        viewModelScope.launch {
            cityRepo.getSeashores(cityId)
                .onSuccess { seashores -> _seashores.value = seashores }
                .onFailure { e ->
                    Log.e(TAG, "seashores load failed.", e)
                    _seashores.value = emptyList()
                }
        }
    }

    companion object {
        private const val TAG = "SeashoreViewModel"
    }
}