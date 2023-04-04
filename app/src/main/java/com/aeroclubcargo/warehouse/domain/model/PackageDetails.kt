package com.aeroclubcargo.warehouse.domain.model

data class PackageDetails(
    var bookingId: String,
    var flightNumber: String,
    var flightDate: String,
    var bookingRefNumber: String,
    var packageItemCategory: Int,
    var cargoPositionType: Int,
    var aircraftConfigType: Int,
    var width: Double,
    var length: Double,
    var height: Double,
    var volumeUnit: String,
    var weight: Double,
    var weightUnit: String,
    var awbTrackingNumber: String,
    var cargoManifestFilePath: String,
)