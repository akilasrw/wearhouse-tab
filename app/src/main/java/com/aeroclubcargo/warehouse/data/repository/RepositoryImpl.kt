package com.aeroclubcargo.warehouse.data.repository

import com.aeroclubcargo.warehouse.data.local.DataStorePreferenceRepository
import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.ApiInterface
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private  val apiInterface: ApiInterface,
    private var datastore: DataStorePreferenceRepository
) : Repository {

    override suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto {
       return apiInterface.authenticateUser(authenticateRequestDto = authenticateRequestDto)
    }

    override suspend fun saveCredential(credentialDto: CredentialDto) {
        datastore.saveCredential(credentialDto = credentialDto)
    }

    override suspend fun getCredential(): Flow<CredentialDto?> {
        return datastore.getCredential
    }

    override suspend fun clearCredential() {
        return datastore.clearCredential()
    }

    override suspend fun saveLoggedInUser(authenticateRequestDto: AuthenticateResponseDto) {
        return datastore.saveAuthenticatedLoggedInUser(authenticateRequestDto = authenticateRequestDto)
    }

    override suspend fun getLoggedInUser(): Flow<AuthenticateResponseDto?> {
        return datastore.authenticatedLoggedInUser
    }


}