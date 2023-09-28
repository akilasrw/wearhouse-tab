package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.PackageLineItem
import com.aeroclubcargo.warehouse.domain.model.Pagination
import com.aeroclubcargo.warehouse.domain.model.ULDModel
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
    suspend fun updatePackage(body: PackageLineItem): Response<Any>
    suspend fun getFlightScheduleWithULDCount(scheduledDepartureStartDateTime : String, scheduledDepartureEndDateTime : String): Response<List<FlightScheduleModel>>
    suspend fun getULDFilteredList( pageIndex:Int, pageSize:Int): Response<Pagination<ULDModel>>

}