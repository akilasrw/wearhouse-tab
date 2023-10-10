package com.aeroclubcargo.warehouse.domain.model

data class PackageItemModel (
    var id:String,
    var packageRefNumber:String,
    var width: Double,
    var length: Double,
    var height: Double,
    var weight: Double,
    var chargeableWeight: Double,
    var volumeUnitId: String,
    var weightUnitId: String,
    var packageItemStatus: Int,
    var description:String,
    var packageItemType: Int,
    var packageItemCategory: Int,
    var cargoBookingId: String,
)