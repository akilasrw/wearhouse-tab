package com.aeroclubcargo.warehouse.domain.model

data class BookingModel(
    var id:String,
    var bookingNumber:String,
    var awbNumber:String,
    var bookingAgent:String,
    var bookingDate : String,
    var weight : String,
    var status : String,
    var dateAndTime : String,
    var bookingStatus : Int,
    var verifyStatus : Int,
    var numberOfBoxes : Int,
    var totalWeight : Double,
    var totalVolume : Double,
    var totalRecWeight : Double,
    var totalRecVolume : Double,
    var numberOfRecBoxes : Int,
    var cargoHandlingInstruction: String,
    var packageItems: List<PackageItemModel>? = null

)