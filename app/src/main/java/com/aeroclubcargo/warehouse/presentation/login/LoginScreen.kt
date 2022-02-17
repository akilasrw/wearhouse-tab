package com.aeroclubcargo.warehouse.presentation.login

import android.os.Handler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.splash.components.ProgressIndicator

@Composable
fun LoginScreen(
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {
        Column() {

            Text(
                text = stringResource(R.string.welcome_back),
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Image(painter = painterResource(id = R.drawable.ic_flight_with_cloud), contentDescription = null,
            )
        }








    }
}