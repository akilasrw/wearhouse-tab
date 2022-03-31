package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import okhttp3.HttpUrl
import retrofit2.http.Body

interface Remote {
    suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto
    suspend fun refreshToken(refreshToken:String): AuthenticateResponseDto
    suspend fun apiGetSectorsList(): String

}