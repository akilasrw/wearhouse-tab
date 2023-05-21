package com.aeroclubcargo.warehouse.domain.model

data class CutOffTimeModel(
    var flightNo:String,
    var departureDate:String,
    var departureTime:String,
    var cutOffTime:String,
    var origin: String,
    var dest: String,
    var airCraftType:String,
    var totalBookVolume: Double,
    var totalBookWeight:Double,

    )
