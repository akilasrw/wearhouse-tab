package com.aeroclubcargo.warehouse.data.remote

import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("/User/authenticate")
    suspend fun authenticateUser(@Body authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto

}