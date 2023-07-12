package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants

data class PackageDetails(
    var id: String,
    var scheduledDepartureDateTime: String,
    var flightNumber: String,
    var aircraftSubTypeName: String,
    var cutoffTimeMin: Int?,
    var origin: String,
    var destination: String,
    var bookingNumber: String,
    var awbNumber: String,
    var bookingAgent: String,
    var bookingDate: String,
    @Constants.BookingStatus
    var bookingStatus: Int,
    var verifyStatus: Int,
    var numberOfBoxes: Int,
    var totalWeight: Double,
    var totalVolume: Double,
    var numberOfRecBoxes: Int,
    var totalRecWeight: Double,
    var totalRecVolume: Double,
    var packageItems: List<PackageLineItem>?,

)