package com.aeroclubcargo.warehouse.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.cutoff_time.CutOffTimeScreen
import com.aeroclubcargo.warehouse.presentation.dashboard.DashboardScreen
import com.aeroclubcargo.warehouse.presentation.flight_schedule.FlightScheduleScreen
import com.aeroclubcargo.warehouse.presentation.login.LoginScreen
import com.aeroclubcargo.warehouse.presentation.scan_cargo.ScanCargoScreen
import com.aeroclubcargo.warehouse.presentation.splash.SplashScreen
import com.aeroclubcargo.warehouse.presentation.uld_assignment.ULDAssignmentScreen
import com.aeroclubcargo.warehouse.presentation.uld_master.ULDMasterScreen
import com.aeroclubcargo.warehouse.presentation.update_booking.UpdateBookingScreen
import com.aeroclubcargo.warehouse.presentation.verify_booking.VerifyBookingScreen
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


@Composable
fun navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(navController)
        }
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController)
        }
        composable(
            route = Screen.DashboardScreen.route
        ) {
            DashboardScreen(navController)
        }
        composable(
            route = Screen.ScanCargoScreen.route
        ) {
            ScanCargoScreen(navController)
        }
        composable(
            route = Screen.VerifyBookingScreen.route+"/{parameter}",
            arguments = listOf(navArgument("parameter") { type = NavType.StringType })
        ) { backStackEntry -> VerifyBookingScreen(navController,backStackEntry.arguments?.getString("parameter")) }
        composable(
            route = Screen.UpdateBookingScreen.route
        ) {
            UpdateBookingScreen(navController)
        }
        composable(
            route = Screen.CutOffTimeScreen.route
        ) {
            CutOffTimeScreen(navController)
        }
        composable(
            route = Screen.ULDMasterScreen.route
        ){
            ULDMasterScreen(navController)
        }
        composable(
            route = Screen.FlightScheduleScreen.route
        ){
            FlightScheduleScreen(navController)
        }
        composable(
            route = Screen.ULDAssignmentScreen.route+"/{parameter}",
            arguments = listOf(navArgument("parameter") { type = NavType.StringType }),
        ){ backStackEntry ->
            val userJson =  backStackEntry.arguments?.getString("parameter")
             val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            val jsonAdapter = moshi.adapter(FlightScheduleModel::class.java).lenient()
            val userObject = jsonAdapter.fromJson(userJson)
            ULDAssignmentScreen(navController,userObject)
        }
    }
}