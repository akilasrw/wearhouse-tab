package com.aeroclubcargo.warehouse.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.domain.use_case.locale.GetLocaleUseCase
import com.aeroclubcargo.warehouse.domain.use_case.locale.SaveLocaleUseCase
import com.aeroclubcargo.warehouse.domain.use_case.login.LoginUseCase
import com.aeroclubcargo.warehouse.utils.isValidEmailAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    var getLocaleUseCase: GetLocaleUseCase,
    var saveLocaleUseCase: SaveLocaleUseCase,
    var loginUseCase: LoginUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _language = MutableLiveData(1)
    var language: LiveData<Int> = _language

    private val _loginState = MutableLiveData(LoginState())
    var loginState: LiveData<LoginState> = _loginState

    fun onDialogDismiss() {
        _loginState.postValue(LoginState())
    }

    init {
        getLocale()
    }

    private fun getLocale() {
        getLocaleUseCase().onEach {
            _language.value = it.data
        }.launchIn(viewModelScope)
    }

    fun saveLocale(localeIndex: Int) {
        saveLocaleUseCase(localeIndex).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.e("LoginViewModel", "language saved ${result.data}")
                }
                is Resource.Error -> result.let { Log.e("LoginViewModel", "${result.message}") }
                is Resource.Loading -> Log.e("LoginViewModel", "saving locale")
            }
        }.launchIn(viewModelScope)
    }

    fun authenticateUser(context: Context, userName: String, password: String) {
        if (userName.isNullOrEmpty()) {
            _loginState.postValue(LoginState(error = context.getString(R.string.plz_insert_user_name)))
        } else if (!userName.isValidEmailAddress()) {
            _loginState.postValue(LoginState(error = context.getString(R.string.plz_insert_valid_email)))
        } else if (password.isNullOrEmpty()) {
            _loginState.postValue(LoginState(error = context.getString(R.string.plz_insert_password)))
        } else {
            loginUseCase(username = userName, password = password).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _loginState.postValue(LoginState(isLoginSuccess = true))
                    }
                    is Resource.Error -> {
                        _loginState.postValue(LoginState(error = result.message))

                    }
                    is Resource.Loading -> {
                        _loginState.postValue(LoginState(isLoading = true))

                    }
                }
            }.launchIn(viewModelScope)
        }
    }


}