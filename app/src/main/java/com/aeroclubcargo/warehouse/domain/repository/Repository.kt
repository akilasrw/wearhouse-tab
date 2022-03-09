package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.data.local.dto.RememberMeDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import kotlinx.coroutines.flow.Flow

interface Repository {

     suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto

     suspend fun saveRememberMeCredential(rememberMeDto: RememberMeDto)
     suspend fun getRememberMeCredential(): Flow<RememberMeDto?>
     suspend fun clearCredential()
}