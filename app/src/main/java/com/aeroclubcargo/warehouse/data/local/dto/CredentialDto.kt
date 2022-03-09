package com.aeroclubcargo.warehouse.data.local.dto

import com.aeroclubcargo.warehouse.domain.model.RememberMe

data class CredentialDto (var userName:String, var password:String, var isRememberMe: Boolean)

fun CredentialDto.toRememberMe(): RememberMe {
    return RememberMe(userName = userName, password = password, isRememberMe = isRememberMe)
}