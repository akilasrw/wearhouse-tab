package com.aeroclubcargo.warehouse.data.remote

import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.BookingStatusUpdateRequest
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.model.PackageListItem
import com.aeroclubcargo.warehouse.domain.model.Pagination
import retrofit2.http.*

interface ApiInterface {

    @POST("api/${Constants.API_VERSION}/User/authenticate")
    suspend fun authenticateUser(@Body authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto

    @POST("api/${Constants.API_VERSION}/User/mobile/refresh-token")
    suspend fun refreshToken(@Body refreshToken: String): AuthenticateResponseDto

    @GET("api/${Constants.API_VERSION}/Airport/getSelectList")
    suspend fun apiGetSectors(): String

    @GET("api/${Constants.API_VERSION}/Package/GetFilteredList")
    suspend fun getCargoPackageList(
        @Query("PageSize") pageSize: Int,
        @Query("PageIndex") pageIndex: Int,
        @Query("IncludeCargoBooking") includeCargoBooking: Boolean,
    ): Pagination<PackageListItem>

    @GET("api/${Constants.API_VERSION}/Package")
    suspend fun getPackageDetails(
        @Query("PackageRefNumber") packageRefNumber: String
    ): PackageDetails

    @POST("api/${Constants.API_VERSION}/CargoBooking")
    suspend fun acceptCargoBooking(@Body bookingStatus: BookingStatusUpdateRequest): Boolean


    @POST("api/${Constants.API_VERSION}/CargoBooking")
    suspend fun updateULDCargoBooking(@Body bookingStatus: BookingStatusUpdateRequest): Boolean


}