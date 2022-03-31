package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import kotlinx.coroutines.flow.Flow

interface Local {
    suspend fun saveCredential(credentialDto: CredentialDto)
    suspend fun getCredential(): Flow<CredentialDto?>
    suspend fun clearCredential()
    suspend fun removeLoginUserDetails()

    suspend fun saveLoggedInUser(authenticateRequestDto: AuthenticateResponseDto)
    suspend fun saveJwtToken(jwtToken: String)
    suspend fun getJwtToken(): Flow<String>
    suspend fun getLoggedInUser(): Flow<AuthenticateResponseDto?>
}