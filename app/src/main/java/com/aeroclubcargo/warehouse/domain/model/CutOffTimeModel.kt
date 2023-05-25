package com.aeroclubcargo.warehouse.domain.model

data class CutOffTimeModel(
    var id:String,
    var flightNumber:String? = null,
    var scheduledDepartureDateTime:String? = null,
    var originAirportCode:String? = null,
    var destinationAirportCode:String? = null,
    var originAirportName: String? = null,
    var destinationAirportName: String? = null,
    var aircraftRegNo:String? = null,
    var aircraftTypes: Int? = 0,
    var totalBookedWeight:Double?= 0.0,
    var totalBookedVolume:Double? = 0.0,
    var aircraftConfigurationType:Int? = null,
    var aircraftSubTypeName:String? = null,
    var cutoffTimeMin:Int? = 0,
    )