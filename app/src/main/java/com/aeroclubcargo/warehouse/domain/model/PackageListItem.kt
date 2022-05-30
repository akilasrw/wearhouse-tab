package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants

data class PackageListItem(

    val id: String,
    val packageRefNumber: String,
    val flightNumber: String,
    val bookingDate: String,
    @Constants.BookingStatus
    val bookingStatus: Int= 0,
    val height: Double,
    val width: Double,
    val length: Double,
    val weight: Double,
)

fun PackageListItem.getStatusString(): String {
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

