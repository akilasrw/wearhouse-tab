package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants

data class CargoBooking(
    val bookingDate: String,
    val bookingNumber: String,
    @Constants.BookingStatus
    val bookingStatus: Int= 0,
    val destinationAirportCode: String,
    val destinationAirportId: String,
    val flightDate: String,
    val flightNumber: String,
    val id: String,
    val numberOfBoxes: Int,
    val totalWeight: Int
)

fun CargoBooking.getStatusString(): String {
    when (bookingStatus) {
        Constants.None -> {
            return "None";
        }
        Constants.Pending -> {
            return "Pending";
        }
        Constants.Accepted -> {
            return "Accepted";
        }
        Constants.Loading -> {
            return "Loading";
        }
        Constants.Invoiced -> {
            return "Invoiced";
        }
        Constants.Dispatched -> {
            return "Dispatched";
        }
        Constants.Exported -> {
            return "Exported";
        }
    }
    return ""
}

