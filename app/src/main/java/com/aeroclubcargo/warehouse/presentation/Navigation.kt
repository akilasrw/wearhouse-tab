package com.aeroclubcargo.warehouse.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.presentation.add_lir_data.lir_detail.LirDetailScreen
import com.aeroclubcargo.warehouse.presentation.add_lir_data.schedule_list.LirScheduleListScreen
import com.aeroclubcargo.warehouse.presentation.cargo_assignment.CargoAssignmentScreen
import com.aeroclubcargo.warehouse.presentation.chat.ChatScreen
import com.aeroclubcargo.warehouse.presentation.cutoff_time.CutOffTimeScreen
import com.aeroclubcargo.warehouse.presentation.dashboard.DashboardScreen
import com.aeroclubcargo.warehouse.presentation.flight_schedule.FlightScheduleScreen
import com.aeroclubcargo.warehouse.presentation.login.LoginScreen
import com.aeroclubcargo.warehouse.presentation.pdf_viewer.RenderHTMLInWebView
import com.aeroclubcargo.warehouse.presentation.receive_cargo.ReceiveCargoListScreen
import com.aeroclubcargo.warehouse.presentation.recieved_cargo_details.ReceivedCargoDetailScreen
import com.aeroclubcargo.warehouse.presentation.scan_cargo.ScanCargoScreen
import com.aeroclubcargo.warehouse.presentation.splash.SplashScreen
import com.aeroclubcargo.warehouse.presentation.uld_assignment.ULDAssignmentScreen
import com.aeroclubcargo.warehouse.presentation.uld_master.ULDMasterScreen
import com.aeroclubcargo.warehouse.presentation.uld_position.FlightScheduleScreenForULD
import com.aeroclubcargo.warehouse.presentation.uld_position.ULDPositionScreen
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
            route = Screen.VerifyBookingScreen.route + "/{parameter}",
            arguments = listOf(navArgument("parameter") { type = NavType.StringType })
        ) { backStackEntry ->
            VerifyBookingScreen(
                navController,
                backStackEntry.arguments?.getString("parameter")
            )
        }
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
        ) {
            ULDMasterScreen(navController)
        }
        composable(route = Screen.ULDPositionScreen.route + "/{parameter}",
            arguments = listOf(navArgument("parameter") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("parameter")
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter = moshi.adapter(FlightScheduleModel::class.java).lenient()
            val scheduleModel = jsonAdapter.fromJson(userJson)
            ULDPositionScreen(navController = navController, scheduleModel = scheduleModel)
        }
        composable(
            route = Screen.FlightScheduleScreen.route,
        ) {
            FlightScheduleScreen(navController)
        }
        composable(
            route = Screen.ReceiveCargoListScreen.route
        ) {
            ReceiveCargoListScreen(navController)
        }
        composable(
            route = Screen.ReceivedCargoDetailScreen.route + "/{parameter}",
            arguments = listOf(navArgument("parameter") { type = NavType.StringType }),
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("parameter")
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter = moshi.adapter(FlightScheduleModel::class.java).lenient()
            val userObject = jsonAdapter.fromJson(userJson)
            ReceivedCargoDetailScreen(navController, userObject)
        }
        composable(
            route = Screen.ULDAssignmentScreen.route + "/{parameter}",
            arguments = listOf(navArgument("parameter") { type = NavType.StringType }),
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("parameter")
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter = moshi.adapter(FlightScheduleModel::class.java).lenient()
            val userObject = jsonAdapter.fromJson(userJson)
            ULDAssignmentScreen(navController, userObject)
        }
        composable(route = Screen.ChatScreen.route) {
            ChatScreen(
                navController = navController
            )
        }
        composable(route = Screen.LirScheduleListScreen.route) {
            LirScheduleListScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.LirDetailScreen.route + "/{parameter}",
            arguments = listOf(navArgument("parameter") { type = NavType.StringType }),
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("parameter")
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter = moshi.adapter(FlightScheduleModel::class.java).lenient()
            val userObject = jsonAdapter.fromJson(userJson)
            LirDetailScreen(
                navController = navController,
                scheduleModel = userObject
            )
        }
        composable(route = Screen.FlightScheduleListForUldScreen.route) {
            FlightScheduleScreenForULD(navController = navController)
        }
        composable(
            route = Screen.CargoAssignmentScreen.route + "/{cargoModel}/{flightScheduleSector}",
            arguments = listOf(
                navArgument("cargoModel") { type = NavType.StringType },
                navArgument("flightScheduleSector") { type = NavType.StringType })
        ) { backStack ->
            val flightScheduleSector = backStack.arguments?.getString("flightScheduleSector")
            val cargoModel = backStack.arguments?.getString("cargoModel")
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter = moshi.adapter(ULDPalletVM::class.java).lenient()
            val userObject = jsonAdapter.fromJson(cargoModel)
            CargoAssignmentScreen(
                navController = navController,
                uldPalletVM = userObject,
                flightScheduleSector = flightScheduleSector,
            )
        }
        composable(
            route = Screen.PDFViewScreen.route,
//            route = Screen.PDFViewScreen.route + "/{parameter}",
//            arguments = listOf(navArgument("parameter") { type = NavType.StringType }),
        ) {
//                backStackEntry ->
//            val htmlContent = backStackEntry.arguments?.getString("parameter")
            RenderHTMLInWebView(navController = navController)
        }
    }
}