package com.aeroclubcargo.warehouse.domain.model

data class CutOffTimeRequest(var id:String,
                             val flightId : String,
                             var cutOffTimeMin:Int,)
