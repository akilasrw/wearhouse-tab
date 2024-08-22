package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants

data class PackageListItem(

    val id: String,
    val packageRefNumber: String,
    val flightNumber: String,
    val bookingDate: String,
    @Constants.BookingStatus
    val packageItemStatus: Int,
    val height: Double,
    val width: Double,
    val length: Double,
    val weight: Double,
)

fun PackageListItem.getStatusString(): String {
    when (packageItemStatus) {
        Constants.Booking_Made -> {
           return "Booked";
        }
        Constants.PickedUp -> {
            return "PickedUp";
        }
        Constants.Returned -> {
            return "Returned";
        }
        Constants.Cargo_Received -> {
            return "Cargo_Received";
        }
        Constants.AcceptedForFLight -> {
            return "AcceptedForFLight";
        }
        Constants.Offloaded -> {
            return "Offloaded";
        }
        Constants.FlightDispatched -> {
            return "FlightDispatched";
        }
        Constants.Arrived -> {
            return "Arrived";
        }
        Constants.IndestinationWarehouse -> {
            return "IndestinationWarehouse";
        }
        Constants.TruckForDelivery -> {
            return "TruckForDelivery";
        }
        Constants.Deliverd -> {
            return "Deliverd";
        }

    }
    return ""
}

