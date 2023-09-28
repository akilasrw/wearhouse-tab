package com.aeroclubcargo.warehouse.domain.model

data class ULDModel(
    var id:String,
    var uldType:Int,
    var uldLocateStatus:Int,
    var serialNumber: String,
    var uldOwnershipType: Int,
    var ownerAirlineCode: String?,
    var lendAirlineCode: String?,
    var lastUsedDate: String,
    var lastUsedFlightNumber: String,
    var lastLocatedAirportCode: String,
    var uldMetaDataId: String,
    var width: Double,
    var length: Double,
    var height: Double,
    var weight: Double,
    var maxWeight: Double,
    var maxVolume: Double
    )
