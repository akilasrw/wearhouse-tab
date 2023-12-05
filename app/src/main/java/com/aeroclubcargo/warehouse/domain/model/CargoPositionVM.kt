package com.aeroclubcargo.warehouse.domain.model

data class CargoPositionVM(
    var id:String,
    var name: String,
    var zoneAreaId: String,
    var cargoPositionType: Int,
    var maxWeight: Double,
    var currentWeight: Double,
    var maxVolume: Double,
    var currentVolume: Double,
    var seatId: String,
    var overheadCompartmentId: String,
    var height: Double,
    var length: Double,
    var breadth: Double,
    var priority: Double,
    var flightLeg: Double,
)