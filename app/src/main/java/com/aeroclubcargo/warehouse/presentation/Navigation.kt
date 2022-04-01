package com.aeroclubcargo.warehouse.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aeroclubcargo.warehouse.presentation.dashboard.DashboardScreen
import com.aeroclubcargo.warehouse.presentation.login.LoginScreen
import com.aeroclubcargo.warehouse.presentation.scan_cargo.ScanCargoScreen
import com.aeroclubcargo.warehouse.presentation.splash.SplashScreen


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
    }
}