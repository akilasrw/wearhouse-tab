package com.aeroclubcargo.warehouse.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import com.aeroclubcargo.warehouse.common.navigator.GlobalNavigationHandler
import com.aeroclubcargo.warehouse.common.navigator.GlobalNavigator
import com.aeroclubcargo.warehouse.theme.SkyTechCargoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(),GlobalNavigationHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkyTechCargoTheme {
               navigation()
            }
        }
    }

    override fun onStart() {
        GlobalNavigator.registerHandler(this)
        super.onStart()
    }

    override fun onStop() {
        GlobalNavigator.unregisterHandler()
        super.onStop()
    }

    override fun logout() {
        val intent =Intent(baseContext,MainActivity::class.java)
        startActivity(intent)
    }
}