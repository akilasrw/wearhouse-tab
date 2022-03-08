package com.aeroclubcargo.warehouse.domain.model

data class AuthenticateResponse(
    var id:String,
//    var firstName: String,
//    var lastName : String,
//    var username : String,
    var jwtToken : String,
)
