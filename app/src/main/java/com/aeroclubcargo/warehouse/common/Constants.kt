package com.aeroclubcargo.warehouse.common

import androidx.annotation.IntDef
import androidx.datastore.preferences.core.preferencesKey
import com.aeroclubcargo.warehouse.BuildConfig

object Constants {

    //    const val BASE_URL = "https://aeroclub-skytechcargo-app-dev.azurewebsites.net/"
    const val BASE_URL = BuildConfig.API_BASE_URL
    const val API_VERSION = BuildConfig.API_VERSION


    //    const val LANGUAGE_KEY = "language_key"
    val PREF_LANGUAGE = preferencesKey<Int>("language")
    val PREF_REMEMBER_ME = preferencesKey<String>("rememberMeDto")
    val PREF_LOGIN_USER = preferencesKey<String>("loginUser")
    val PREF_JWT_TOKEN = preferencesKey<String>("jwtToken")

    @IntDef(None, Pending, Accepted,Loading,Invoiced,Dispatched,Exported)
    @Retention(AnnotationRetention.SOURCE)
    annotation class BookingStatus

    const val None = 0
    const val Pending = 10
    const val Accepted = 20
    const val Loading = 30
    const val Invoiced = 40
    const val Dispatched = 50
    const val Exported = 60

//    @IntDef(None, Pending, Accepted,Loading,Invoiced,Dispatched,Exported)
//    @Retention(AnnotationRetention.SOURCE)
//    public enum PackageItemCategory
//    {
//        None = 0,
//        General = 1,
//        Animal = 2,
//        Artwork = 3,
//        Dgr = 4
//    }
}