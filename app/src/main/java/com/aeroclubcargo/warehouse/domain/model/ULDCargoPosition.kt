package com.aeroclubcargo.warehouse.domain.model

data class ULDCargoPositionRequest(
    var uldId: String,
    var cargoPositionId: String,
)


data class ULDCargoPositionMap(
    var uldId: String,
    var uldNumber: String,
    var totalWeight: Double,
    var maxWeight : Double,
    var cargoPositionName: String,
    var destination: String? = null,
)

data class ULDCargoPositionResponse(
    var id:String,
    var statusCode: Int,
    var message: String,
)