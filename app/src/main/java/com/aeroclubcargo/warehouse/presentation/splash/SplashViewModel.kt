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
class SplashViewModel @Inject constructor(var loginUseCase: LoginUseCase,var rememberMeUseCase: RememberMeUseCase) : ViewModel() {

    fun silentLogin(navController: NavController) {
        rememberMeUseCase().onEach {
            if(!it.userName.isNullOrEmpty() && !it.password.isNullOrEmpty()){
               loginUseCase.invoke(username = it.userName!!, password = it.password!!).onEach {
                       result ->
                   when(result){
                       is Resource.Success -> {
                           navController.popBackStack()
                           navController.navigate(Screen.DashboardScreen.route)
                       }
                       else -> {
                           navController.popBackStack()
                           navController.navigate(Screen.LoginScreen.route)
                       }
                   }
               }.launchIn(viewModelScope)
            }else{
                navController.popBackStack()
                navController.navigate(Screen.LoginScreen.route)
            }
        }.launchIn(viewModelScope)
    }

}