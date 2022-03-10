package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto

    suspend fun saveCredential(credentialDto: CredentialDto)
    suspend fun getCredential(): Flow<CredentialDto?>
    suspend fun clearCredential()

    suspend fun saveLoggedInUser(authenticateRequestDto: AuthenticateResponseDto)
    suspend fun getLoggedInUser():Flow<AuthenticateResponseDto?>
}