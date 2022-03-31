package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto

data class AuthenticateResponse(
    var id:String,
    var firstName: String,
    var lastName : String,
    var username : String,
    var jwtToken : String?,
    var refreshToken:String?
)

fun AuthenticateResponse.toAuthenticateResponseDto() : AuthenticateResponseDto{
    return AuthenticateResponseDto(
        id, firstName, lastName, username, jwtToken,refreshToken
    )
}