package com.aeroclubcargo.warehouse.data.remote

import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.CargoBooking
import com.aeroclubcargo.warehouse.domain.model.Pagination
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("api/${Constants.API_VERSION}/User/authenticate")
    suspend fun authenticateUser(@Body authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto

    @POST("api/${Constants.API_VERSION}/User/mobile/refresh-token")
    suspend fun refreshToken(@Body refreshToken:String): AuthenticateResponseDto

     @GET("api/${Constants.API_VERSION}/Airport/getSelectList")
    suspend fun apiGetSectors(): String

    @GET("api/${Constants.API_VERSION}/CargoBooking/GetFilteredList")
    suspend fun getCargoBooking(@Query("PageSize") pageSize: Int ,@Query("PageIndex") pageIndex : Int) :Pagination<CargoBooking>


}