package com.aeroclubcargo.warehouse.utils

fun Double.toMultiDecimalString(): String {
    return "%.9f".format(this)
}