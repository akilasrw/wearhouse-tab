package com.aeroclubcargo.warehouse.di

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.data.local.DataStorePreferenceRepository
import com.aeroclubcargo.warehouse.data.remote.TokenRefreshAPI
import com.aeroclubcargo.warehouse.presentation.Screen
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import javax.inject.Inject

class AuthenticationInterceptorRefreshToken @Inject constructor(
    var tokenRefreshAPI: TokenRefreshAPI,
    var dataStorePreferenceRepository: DataStorePreferenceRepository,
    var context: Context
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            var token = runBlocking {
                dataStorePreferenceRepository.getToken()
            }
            val originalRequest = chain.request()
            val authenticationRequest =
                originalRequest
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token").build()
            val initialResponse = chain.proceed(authenticationRequest)
            when {
                initialResponse.code == 401 -> {
                    val responseNewTokenLoginModel = runBlocking {
                        var refreshToken = dataStorePreferenceRepository.getRefreshToken()
                        if (refreshToken != null) {
                            try {
                                tokenRefreshAPI.refreshToken(refreshToken)
                            }catch (e:Exception){
                                e.printStackTrace()
                                null
                            }
                        } else {
                            null
                        }
                    }
                    return when {
                        responseNewTokenLoginModel == null -> {

                            // TODO sign out application
                            Log.e("AUTH ERROR", "responseNewTokenLoginModel")
                            initialResponse
                        }
                        else -> {
                            runBlocking {
                                dataStorePreferenceRepository.saveAuthenticatedLoggedInUser(
                                    authenticateRequestDto = responseNewTokenLoginModel
                                )
                                dataStorePreferenceRepository.saveJwtToken(responseNewTokenLoginModel.jwtToken!!)
                            }
                            chain.proceed(authenticationRequest)
                        }
                    }
                }
                else -> {
                    return initialResponse
                }
            }
        }
    }

}