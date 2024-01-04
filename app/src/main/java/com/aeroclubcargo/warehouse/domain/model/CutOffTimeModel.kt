package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants

data class CutOffTimeModel(
    var id:String,
    var flightNumber:String? = null,
    var scheduledDepartureDateTime:String? = null,
    var originAirportCode:String? = null,
    var destinationAirportCode:String? = null,
    var originAirportName: String? = null,
    var destinationAirportName: String? = null,
    var aircraftRegNo:String? = null,
    var totalBookedWeight:Double?= 0.0,
    var totalBookedVolume:Double? = 0.0,
    var aircraftConfigurationType:Int? = null,
    var aircraftSubTypeName:String? = null,
    var cutoffTimeMin:Int? = 0,
    var aircraftType: Int? = null,
    )