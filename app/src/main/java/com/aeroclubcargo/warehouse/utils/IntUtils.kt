package com.aeroclubcargo.warehouse.utils


fun Int.toDurationTime() : String {
    val hours = this / 60
    val remainingMinutes = this % 60
    return String.format("%02d:%02d", hours, remainingMinutes)
}