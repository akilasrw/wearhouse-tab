package com.aeroclubcargo.warehouse.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.common.Resource
import com.aeroclubcargo.warehouse.domain.use_case.login.LoginUseCase
import com.aeroclubcargo.warehouse.domain.use_case.login.RememberMeUseCase
import com.aeroclubcargo.warehouse.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(var loginUseCase: LoginUseCase) : ViewModel() {

    fun silentLogin(navController: NavController) {
               loginUseCase.silentLogin().onEach {
                       result ->
                   when(result){
                       is Resource.Success -> {
                           navController.popBackStack()
                           navController.navigate(Screen.DashboardScreen.route)
                       }
                       is Resource.Error -> {
                           navController.popBackStack()
                           navController.navigate(Screen.LoginScreen.route)
                       }
                       else -> {

                       }
                   }
               }.launchIn(viewModelScope)
    }

}