package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants

data class CargoBooking(
    val bookingDate: String,
    val bookingNumber: String,
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
        Constants.BookingStatus.None.ordinal -> {
            return "None";
        }
        Constants.BookingStatus.Pending.ordinal -> {
            return "Pending";
        }
        Constants.BookingStatus.Accepted.ordinal -> {
            return "Accepted";
        }
        Constants.BookingStatus.Loading.ordinal -> {
            return "Loading";
        }
        Constants.BookingStatus.Invoiced.ordinal -> {
            return "Invoiced";

        }
        Constants.BookingStatus.Dispatched.ordinal -> {
            return "Dispatched";
        }
        Constants.BookingStatus.Exported.ordinal -> {
            return "Exported";
        }

    }
    return ""
}

