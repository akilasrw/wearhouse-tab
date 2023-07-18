package com.aeroclubcargo.warehouse.utils

import java.sql.Struct
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.time.Duration.Companion.hours

const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
fun String.isValidEmailAddress(): Boolean {
    return (matches(Regex(emailPattern)) && length > 0)
}

//val dateString = "2023-06-05" // MMM d, yyyy HH:mm a
fun String.toDateTimeDisplayFormat(outputFormat: String) : String? {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") // Define the format of the input string
        val date = dateFormat.parse(this)
//        val formattedTime = dateFormat.format(date)
        val outputFormat = SimpleDateFormat(outputFormat) // TODO
        if (date != null) {
            outputFormat.format(date)
        }else{
            null
        }
    }catch (e:Exception){
        null
    }
}

// string date format 2023-07-02T10:30:00
// time must be in 24 h format
fun String.updateTimeOnly(hours:Int, minutes:Int) : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val parsedDate: Date = dateFormat.parse(this) ?: return this

    val calendar = Calendar.getInstance()
    calendar.time = parsedDate

    calendar.set(Calendar.MINUTE, minutes)
    calendar.set(Calendar.HOUR, hours)
    return dateFormat.format(calendar.time)
}

fun String.toDateTimeHour() : Int?{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    try {
        val parsedDate: Date = dateFormat.parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = parsedDate
        return calendar.get(Calendar.HOUR_OF_DAY)
    }catch (e:Exception){
        return 0
    }
}

fun String.toDateTimeMin() : Int?{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    try {
        val parsedDate: Date = dateFormat.parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = parsedDate
        return calendar.get(Calendar.MINUTE)
    }catch (e:Exception){
        return 0
    }
}