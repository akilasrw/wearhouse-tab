package com.aeroclubcargo.warehouse.domain.model

data class Booking(
    var serialNo:String,
    var flightNo:String,
    var dimensions : String,
    var weight : String,
    var status : String,
    var dateAndTime : String,
)
