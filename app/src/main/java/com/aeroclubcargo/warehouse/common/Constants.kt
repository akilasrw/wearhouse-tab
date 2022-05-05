package com.aeroclubcargo.warehouse.common

import androidx.datastore.preferences.core.preferencesKey
import com.aeroclubcargo.warehouse.BuildConfig

object Constants {

//    const val BASE_URL = "https://aeroclub-skytechcargo-app-dev.azurewebsites.net/"
    const val BASE_URL = BuildConfig.API_BASE_URL


//    const val LANGUAGE_KEY = "language_key"
    val PREF_LANGUAGE = preferencesKey<Int>("language")
    val PREF_REMEMBER_ME = preferencesKey<String>("rememberMeDto")
    val PREF_LOGIN_USER = preferencesKey<String>("loginUser")
    val PREF_JWT_TOKEN = preferencesKey<String>("jwtToken")

//    enum class Language(name: String) {
//        English("English"),
//        Vietnam("Vietnam"),
//    }

    enum class BookingStatus
    {
        None ,
        Pending,
        Accepted,
        Loading ,
        Invoiced,
        Dispatched ,
        Exported
    }



}