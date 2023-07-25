package com.aeroclubcargo.warehouse.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

object Utils{

    fun getDateBeforeOneMonth(): String {
        val time = Calendar.getInstance().time
        time.month = time.month -1
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(time)
    }

    fun getTodayDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(time)
    }
}
