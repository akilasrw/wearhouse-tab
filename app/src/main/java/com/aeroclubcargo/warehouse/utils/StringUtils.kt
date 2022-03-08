package com.aeroclubcargo.warehouse.utils

const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
fun String.isValidEmailAddress(): Boolean {
    return (matches(Regex(emailPattern)) && length > 0)
}
