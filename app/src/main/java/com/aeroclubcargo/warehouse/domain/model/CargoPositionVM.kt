package com.aeroclubcargo.warehouse.domain.model

data class CargoPositionVM(
    var id:String,
    var name: String,
    var zoneAreaId: String,
    var cargoPositionType: Int,
    var maxWeight: Int,
    var currentWeight: Int,
    var maxVolume: Int,
    var currentVolume: Int,
    var seatId: String,
    var overheadCompartmentId: String,
    var height: Int,
    var length: Int,
    var breadth: Int,
    var priority: Int,
    var flightLeg: Int,
)