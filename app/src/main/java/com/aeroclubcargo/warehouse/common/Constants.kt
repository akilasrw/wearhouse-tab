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

    @IntDef(None, Pending, Accepted, Loading, Invoiced, Dispatched, Exported)
    @Retention(AnnotationRetention.SOURCE)
    annotation class BookingStatus

    const val None = 0
    const val Pending = 10
    const val Accepted = 20
    const val Loading = 30
    const val Invoiced = 40
    const val Dispatched = 50
    const val Exported = 60


    @IntDef(None, Pending, Accepted, Loading, Invoiced, Dispatched, Exported)
    @Retention(AnnotationRetention.SOURCE)
    annotation class AircraftTypes {
        companion object {
            val None = 0
            val B7879 = 1
            val A320200 = 2
            val B737400 = 3
            val B737800 = 4
            val B737300 = 5

            fun getAirCraftType(aircraftTypes: Int?): String {
                if (aircraftTypes == null) {
                    return "-"
                }
                return when (aircraftTypes) {
                    None -> "None"
                    B7879 -> "Boeing 787-9"
                    A320200 -> "Airbus A320-200"
                    B737400 -> "Boeing 737-400"
                    B737800 -> "Boeing 737-800"
                    B737300 -> "Boeing 737-300"
                    else -> "Unknown Aircraft Type"
                }
            }
        }
    }


    enum class PackageItemStatus {
        None,
        Booked,
        Accepted,
        Dispatched
    }

    enum class AircraftConfigType {
        None,
        P2C,
        Freighter,
    }

    enum class PackageItemCategory {
        None,
        General,
        Animal,
        Artwork,
        Dgr
    }

    enum class ULDType {
        None,
        Pallet,
        Container
    }

    fun getULDType(type: Int?): String {
        if (type == null) {
            return ""
        }
        when (ULDType.values()[type]) {
            ULDType.None -> {
                return "None"
            }

            ULDType.Pallet -> {
                return "Pallet"
            }

            ULDType.Container -> {
                return "Container"
            }
        }
    }


    fun getPackageItemCategory(packageType: Int): String {
        return when (PackageItemCategory.values()[packageType]) {
            PackageItemCategory.None -> "None"
            PackageItemCategory.General -> "General"
            PackageItemCategory.Animal -> "Animal"
            PackageItemCategory.Artwork -> "Artwork"
            PackageItemCategory.Dgr -> "Dangerous Goods"
        }
    }

    enum class CargoPositionType {
        None,
        OnFloor,
        OnSeat,
        UnderSeat,
        Overhead
    }

    enum class ULDLocateStatus {
        None,
        OnGround,
        OnBoard,
        Maintenance,
        Lend,
    }

    fun getCargoType(type: Int?): String {
        if (type == null) {
            return ""
        }
        when (CargoPositionType.values()[type]) {
            CargoPositionType.None -> {
                return "None"
            }

            CargoPositionType.OnFloor -> {
                return "On Floor"
            }

            CargoPositionType.OnSeat -> {
                return "On Seat"
            }

            CargoPositionType.UnderSeat -> {
                return "UnderSeat"
            }

            CargoPositionType.Overhead -> {
                return "Overhead"
            }
        }
    }
}

enum class ServiceResponseStatus {
    Success,
    ValidationError,
    Failed,
}