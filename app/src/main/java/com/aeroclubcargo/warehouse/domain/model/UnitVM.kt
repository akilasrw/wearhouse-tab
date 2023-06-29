package com.aeroclubcargo.warehouse.domain.model

data class UnitVM(
    var id:String,
    var name:String,
    var unitType : Int
){
    override fun toString(): String {
        return name
    }
}
