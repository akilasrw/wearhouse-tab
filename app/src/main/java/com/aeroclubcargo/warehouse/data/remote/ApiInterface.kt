package com.aeroclubcargo.warehouse.data.remote

import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.CargoBooking
import com.aeroclubcargo.warehouse.domain.model.Pagination
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("api/v1/User/authenticate")
    suspend fun authenticateUser(@Body authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto

    @POST("api/v1/User/mobile/refresh-token")
    suspend fun refreshToken(@Body refreshToken:String): AuthenticateResponseDto

     @GET("api/v1/Airport/getSelectList")
    suspend fun apiGetSectors(): String

    @GET("api/v1/CargoBooking/GetFilteredList")
    suspend fun getCargoBooking(@Query("PageSize") pageSize: Int ,@Query("PageIndex") pageIndex : Int) :Pagination<CargoBooking>


}