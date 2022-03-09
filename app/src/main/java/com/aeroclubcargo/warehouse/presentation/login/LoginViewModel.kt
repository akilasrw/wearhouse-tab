package com.aeroclubcargo.warehouse.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.domain.use_case.locale.GetLocaleUseCase
import com.aeroclubcargo.warehouse.domain.use_case.locale.SaveLocaleUseCase
import com.aeroclubcargo.warehouse.domain.use_case.login.LoginUseCase
import com.aeroclubcargo.warehouse.domain.use_case.login.RememberMeUseCase
import com.aeroclubcargo.warehouse.utils.isValidEmailAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var getLocaleUseCase: GetLocaleUseCase,
    private var saveLocaleUseCase: SaveLocaleUseCase,
    private var rememberMeUseCase: RememberMeUseCase,
    private var loginUseCase: LoginUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _language = MutableLiveData(1)
    var language: LiveData<Int> = _language

    private val _loginState = MutableLiveData(LoginState())
    var loginState: LiveData<LoginState> = _loginState

//    private val _rememberMeState = MutableLiveData(RememberMe(isRememberMe = false))
//    var rememberMeState: LiveData<RememberMe> = _rememberMeState

    private val _rememberMeCheckState = MutableStateFlow(false)
    val rememberMeCheckState = _rememberMeCheckState.asStateFlow()

    private val _emailValue = MutableStateFlow("")
    val emailValue = _emailValue.asStateFlow()

    private val _passwordValue = MutableStateFlow("")
    val passwordValue = _passwordValue.asStateFlow()


    fun onDialogDismiss() {
        _loginState.postValue(LoginState())
    }

    fun onRememberCheckState(rememberMeCheckState: Boolean) {
        _rememberMeCheckState.value = rememberMeCheckState
    }

    fun onEmailChange(emailValue: String) {
        _emailValue.value = emailValue
    }

    fun onPasswordChange(password: String) {
        _passwordValue.value = password
    }

    init {
        getLocale()
        getRememberMeDetails()
    }

    private fun getRememberMeDetails() {
        rememberMeUseCase().onEach {
            it.let {
                _emailValue.value = it.userName ?: ""
                _passwordValue.value = it.password ?: ""
                _rememberMeCheckState.value = it.isRememberMe
            }


        }.launchIn(viewModelScope)
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

    fun authenticateUser(
        context: Context,
        userName: String,
        password: String,
        isRememberMe: Boolean
    ) {
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
                        CoroutineScope(IO).launch {
                            if (isRememberMe) {
                                rememberMeUseCase.saveRememberMeCredentials(userName, password)
                            } else {
                                rememberMeUseCase.clearUserDataOnRememberMe()
                            }
                            _loginState.postValue(LoginState(isLoginSuccess = true))
                        }
                    }
                    is Resource.Error -> {
                        _loginState.postValue(
                            LoginState(
                                error = result.message ?: context.getString(
                                    R.string.u_have_entered_invalid_u_and_p
                                )
                            )
                        )

                    }
                    is Resource.Loading -> {
                        _loginState.postValue(LoginState(isLoading = true))

                    }
                }
            }.launchIn(viewModelScope)
        }
    }


}