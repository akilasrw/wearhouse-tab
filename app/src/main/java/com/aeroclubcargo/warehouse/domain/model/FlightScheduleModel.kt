package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class FlightScheduleModel(
    var id: String,
    var flightNumber: String?,
    var scheduledDepartureDateTime: String?,
    var originAirportCode: String?,
    var destinationAirportCode: String?,
    var aircraftSubTypeName: String?,
    var cutoffTime: String?,
    var uldPositionCount: Int = 0,
    var uldCount: Int = 0,
    @Constants.AircraftTypes var aircraftType: Int? = null,
    var aircraftLayoutId : String?,
)