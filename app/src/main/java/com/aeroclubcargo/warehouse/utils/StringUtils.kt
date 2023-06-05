package com.aeroclubcargo.warehouse.utils

import java.sql.Struct
import java.text.SimpleDateFormat
import java.util.*

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