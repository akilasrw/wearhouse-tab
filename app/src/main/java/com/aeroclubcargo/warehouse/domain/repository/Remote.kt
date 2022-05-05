package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.CargoBooking
import com.aeroclubcargo.warehouse.domain.model.Pagination
import retrofit2.http.Query

interface Remote {
    suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto
    suspend fun refreshToken(refreshToken: String): AuthenticateResponseDto
    suspend fun apiGetSectorsList(): String
    suspend fun getCargoBooking(pageSize: Int, pageIndex: Int) : Pagination<CargoBooking>
}