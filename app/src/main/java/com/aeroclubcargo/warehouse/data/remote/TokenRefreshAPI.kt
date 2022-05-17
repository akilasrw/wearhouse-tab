package com.aeroclubcargo.warehouse.data.remote

import com.aeroclubcargo.warehouse.common.Constants.API_VERSION
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenRefreshAPI {
    @POST("api/$API_VERSION/User/mobile/refresh-token")
    suspend fun refreshToken(@Body refreshToken:String): AuthenticateResponseDto
}