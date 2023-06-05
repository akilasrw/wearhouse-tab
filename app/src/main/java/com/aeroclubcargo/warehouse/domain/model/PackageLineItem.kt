package com.aeroclubcargo.warehouse.domain.model

data class PackageLineItem(
    val id: String,
    val packageRefNumber: String,
    val dimension: String,
    val width: Double,
    val height: Double,
    val length: Double,
    val weight: Double,
    val chargeableWeight: Double,
    val volumeUnitId: String,
    val weightUnitId: String,
    var declaredValue: Int?,
    var packageItemStatus: Int,
    val description: String?,
    var packageItemType: Int,
    val uldContainerId: String,
    var packagePriorityType: Int,
    var packageItemCategory: Int,
    val cargoBookingId: String?,
    val cargoPositionId: String?,
)