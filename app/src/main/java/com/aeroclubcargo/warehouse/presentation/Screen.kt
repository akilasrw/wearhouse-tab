package com.aeroclubcargo.warehouse.presentation

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object DashboardScreen: Screen("dashboard_screen")
    object ScanCargoScreen: Screen("scan_cargo_screen")
    object VerifyBookingScreen: Screen("verify_booking_screen")
    object UpdateBookingScreen: Screen("update_booking_screen")
    object CutOffTimeScreen : Screen("cutt_off_time_screen")
}