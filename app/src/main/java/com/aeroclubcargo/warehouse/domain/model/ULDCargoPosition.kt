package com.aeroclubcargo.warehouse.domain.model

data class ULDCargoPositionRequest(
    var uldId: String,
    var cargoPositionId: String,
)

data class ULDCargoPositionResponse(
    var id:String,
    var statusCode: Int,
    var message: String,
)