package com.aeroclubcargo.warehouse.domain.model

data class ULDPalletVM (
    val id:String,
    val uldType: Int,
    val uldOwnershipType: Int,
    var serialNumber: String,
    var uldMetaDataId: String,
    val ownerAirlineCode: String?,
    val uldLocateStatus: Int,
    val lendAirlineCode:String?,
    var isAssigned: Boolean,
    val allocatedFlightNumber:String?,
    val lastUsedDate:String?,
    val lastUsedFlightNumber:String?,
    val lastLocatedAirportCode:String?,
    val width:Double = 0.0,
    val length:Double = 0.0,
    val height:Double = 0.0,
    val weight:Double = 0.0,
    val maxWeight:Double = 0.0,
    val maxVolume:Double = 0.0
)