package com.aeroclubcargo.warehouse.domain.model

data class PackageLineItem(
    var id: String,
    var packageRefNumber: String,
    var dimension: String,
    var width: Double,
    var height: Double,
    var length: Double,
    var weight: Double,
    var chargeableWeight: Double,
    var volumeUnitId: String,
    var weightUnitId: String,
    var declaredValue: Int?,
    var packageItemStatus: Int,
    var description: String?,
    var packageItemType: Int,
    var uldContainerId: String,
    var packagePriorityType: Int,
    var packageItemCategory: Int,
    var cargoBookingId: String?,
    var cargoPositionId: String?,
)