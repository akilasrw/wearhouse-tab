package com.aeroclubcargo.warehouse.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.data.remote.dto.toAuthenticateResponse
import com.aeroclubcargo.warehouse.domain.model.AuthenticateResponse
import com.aeroclubcargo.warehouse.domain.repository.Repository
import com.aeroclubcargo.warehouse.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

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

    fun logoutUser(navController: NavController){
        CoroutineScope(IO).launch {
            repository.clearCredential()
            repository.removeLoginUserDetails()
        }
        navController.popBackStack()
        navController.navigate(Screen.LoginScreen.route)
    }
    //Test
    fun getSectors() {
        viewModelScope.launch {
            try {
                val user = repository.apiGetSectorsList().first()
//                user!!.refreshToken?.let { repository.refreshToken(refreshToken = it) }
            }catch (e:Exception){
                println(e.localizedMessage)
            }
        }
    }


}