package com.aeroclubcargo.warehouse.domain.model

import com.aeroclubcargo.warehouse.common.Constants

data class BookingStatusUpdateRequest (var id:String,@Constants.BookingStatus var bookingStatus:Int)