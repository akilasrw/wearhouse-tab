package com.aeroclubcargo.warehouse.data.local.dto

import com.aeroclubcargo.warehouse.domain.model.RememberMe

data class RememberMeDto (var userName:String,var password:String)

fun RememberMeDto.toRememberMe(): RememberMe {
    return RememberMe(userName = userName, password = password, isRememberMe = true)
}