package com.aeroclubcargo.warehouse.presentation

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object DashboardScreen: Screen("dashboard_screen")
    object ScanCargoScreen: Screen("scan_cargo_screen")
    object VerifyBookingScreen: Screen("verify_booking_screen")
    object UpdateBookingScreen: Screen("update_booking_screen")
    object CutOffTimeScreen : Screen("cutt_off_time_screen")
    object ULDMasterScreen : Screen("ULD_master_screen")
    object FlightScheduleScreen : Screen("flight_schedule_screen")
    object ULDAssignmentScreen : Screen("uld_assignment_screen")
}

// TODO
// CHANGE TILE ORDERS
/**
 * 1. verify booking -> (rename to) accept Cargo (barcode QR code)
 * 2. flight schedule -> (rename to) Assign ULD
 * 3. received cargo (same screens as update cutoff time)
 * 4

 */
//