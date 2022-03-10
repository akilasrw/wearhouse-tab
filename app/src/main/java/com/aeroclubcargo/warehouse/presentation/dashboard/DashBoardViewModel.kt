package com.aeroclubcargo.warehouse.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.aeroclubcargo.warehouse.data.remote.dto.toAuthenticateResponse
import com.aeroclubcargo.warehouse.domain.model.AuthenticateResponse
import com.aeroclubcargo.warehouse.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(var repository: Repository) : ViewModel() {

    private val _userValue = MutableStateFlow<AuthenticateResponse?>(null)
    val userValue: StateFlow<AuthenticateResponse?> = _userValue.asStateFlow()

    init {
        getLoggedInUser()
    }

    private fun getLoggedInUser() {
        CoroutineScope(IO).launch {
            val user = repository.getLoggedInUser()
            user.collect {
                _userValue.value = it?.toAuthenticateResponse()
            }
        }
    }


}