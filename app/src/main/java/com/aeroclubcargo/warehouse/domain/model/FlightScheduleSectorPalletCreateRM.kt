package com.aeroclubcargo.warehouse.domain.model

data class FlightScheduleSectorPalletCreateRM  (
        var id : String? = null ,
        var flightScheduleSectorId: String,
        var uldId: String,
        var isAdded : Boolean = false
    )

data class FlightScheduleSectorPalletCreateListRM ( var flightScheduleSectorPalletRMs : List<FlightScheduleSectorPalletCreateRM> )

data class FlightScheduleSectorPalletDeleteRM  (
    var flightScheduleSectorId: String,
    var uldId: String,
)