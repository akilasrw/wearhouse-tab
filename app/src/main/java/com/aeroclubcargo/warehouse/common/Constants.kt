package com.aeroclubcargo.warehouse.common

import androidx.datastore.preferences.core.preferencesKey

object Constants {

    const val BASE_URL = "https://aeroclub-skytechcargo-app-dev.azurewebsites.net/"


//    const val LANGUAGE_KEY = "language_key"
    val PREF_LANGUAGE = preferencesKey<Int>("language")
    val PREF_REMEMBER_ME = preferencesKey<String>("rememberMeDto")
    val PREF_LOGIN_USER = preferencesKey<String>("loginUser")

//    enum class Language(name: String) {
//        English("English"),
//        Vietnam("Vietnam"),
//    }

}