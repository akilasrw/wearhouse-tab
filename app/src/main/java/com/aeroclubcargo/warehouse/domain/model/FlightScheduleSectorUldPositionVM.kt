package com.aeroclubcargo.warehouse.domain.model

data class FlightScheduleSectorUldPositionVM(
    var id: String,
    var name: String,
    var zoneAreaId: String?,
    var seatId: String?,
    var overheadCompartmentId: String?,
    var cargoPositionType: Int = 0,
    var maxWeight: Double = 0.0,
    var currentWeight: Double = 0.0,
    var maxVolume: Double = 0.0,
    var currentVolume: Double = 0.0,
    var height: Double = 0.0,
    var length: Double = 0.0,
    var breadth: Double = 0.0,
    var priority: Double = 0.0,
    var flightLeg: Double = 0.0,
    )