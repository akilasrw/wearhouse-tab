package com.aeroclubcargo.warehouse.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.domain.use_case.locale.GetLocaleUseCase
import com.aeroclubcargo.warehouse.domain.use_case.locale.SaveLocaleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    var getLocaleUseCase: GetLocaleUseCase,
    var saveLocaleUseCase: SaveLocaleUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _language = MutableLiveData(1)
    var language: LiveData<Int> = _language

    init {
        getLocale()
    }

    private fun getLocale() {
        getLocaleUseCase().onEach {
            _language.value = it.data
        }.launchIn(viewModelScope)
    }

    fun saveLocale(localeIndex:Int){
        saveLocaleUseCase(localeIndex).onEach { result->
            when (result) {
                is Resource.Success -> {
                    Log.e("LoginViewModel","language saved ${result.data}")
                }

            }
        }.launchIn(viewModelScope)
    }


}