package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants
import com.google.gson.annotations.SerializedName


data class PackageDetails(
    var id: String,
    var scheduledDepartureDateTime: String,
    var flightNumber: String,
    var aircraftSubTypeName: String,
    var cutoffTimeMin: Int?,
    var origin: String,
    var destination: String,
    var bookingNumber: String,
    var destinationAirportCode: String? = null,
    var awbNumber: String,
    var bookingAgent: String,
    var bookingDate: String,
    @Constants.BookingStatus
    var bookingStatus: Int,
    var verifyStatus: Int,
    var numberOfBoxes: Int,
    var totalWeight: Double,
    var totalVolume: Double,
    var numberOfRecBoxes: Int,
    var totalRecWeight: Double,
    var totalRecVolume: Double,
    var awbInformation: AWBInformation? = null,
    var packageItems: List<PackageLineItem>?,
    )

data class AWBInformation(
    @SerializedName("shipperName") var shipperName: String? = null,
    @SerializedName("shipperAccountNumber") var shipperAccountNumber: String? = null,
    @SerializedName("shipperAddress") var shipperAddress: String? = null,
    @SerializedName("consigneeName") var consigneeName: String? = null,
    @SerializedName("consigneeAccountNumber") var consigneeAccountNumber: String? = null,
    @SerializedName("consigneeAddress") var consigneeAddress: String? = null,
    @SerializedName("agentName") var agentName: String? = null,
    @SerializedName("agentCity") var agentCity: String? = null,
    @SerializedName("agentAITACode") var agentAITACode: String? = null,
    @SerializedName("agentAccountNumber") var agentAccountNumber: String? = null,
    @SerializedName("agentAccountInformation") var agentAccountInformation: String? = null,
    @SerializedName("requestedRouting") var requestedRouting: String? = null,
    @SerializedName("routingAndDestinationTo") var routingAndDestinationTo: String? = null,
    @SerializedName("routingAndDestinationBy") var routingAndDestinationBy: String? = null,
    @SerializedName("requestedFlightDate") var requestedFlightDate: String? = null,
    @SerializedName("destinationAirportId") var destinationAirportId: String? = null,
    @SerializedName("destinationAirportName") var destinationAirportName: String? = null,
    @SerializedName("shippingReferenceNumber") var shippingReferenceNumber: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("declaredValueForCarriage") var declaredValueForCarriage: Int? = null,
    @SerializedName("declaredValueForCustomer") var declaredValueForCustomer: Int? = null,
    @SerializedName("amountOfInsurance") var amountOfInsurance: Int? = null,
    @SerializedName("awbTrackingNumber") var awbTrackingNumber: Long? = null,
    @SerializedName("rateCharge") var rateCharge: Int? = null,
    @SerializedName("natureAndQualityOfGoods") var natureAndQualityOfGoods: String? = null,
    @SerializedName("id") var id: String? = null

)