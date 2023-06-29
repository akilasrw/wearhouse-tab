package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.CutOffTimeModel
import com.aeroclubcargo.warehouse.domain.model.Pagination
import com.aeroclubcargo.warehouse.domain.model.UnitVM
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Query

interface Local {
    suspend fun saveCredential(credentialDto: CredentialDto)
    suspend fun getCredential(): Flow<CredentialDto?>
    suspend fun clearCredential()
    suspend fun removeLoginUserDetails()
    suspend fun saveLoggedInUser(authenticateRequestDto: AuthenticateResponseDto)
    suspend fun saveJwtToken(jwtToken: String)
    suspend fun getJwtToken(): Flow<String>
    suspend fun getLoggedInUser(): Flow<AuthenticateResponseDto?>
    suspend fun getUnitList() : Response<List<UnitVM>>

}