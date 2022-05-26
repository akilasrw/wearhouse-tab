package com.aeroclubcargo.warehouse.presentation.verify_booking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.BlueLight2
import com.aeroclubcargo.warehouse.theme.hintLightGray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerifyBookingScreen(
    navController: NavController,
    viewModel: VerifyBookingViewModel = hiltViewModel()
) {
    val numbers = (0..2).toList()
    Scaffold(topBar = {
        GetTopBar(navController = navController)
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
                .background(color = Color.White)
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                modifier = Modifier.fillMaxHeight(fraction = 0.7f)
            ) {
                item {
                    GetTileWidget(hint = "Flight Number", value = "AC-101")
                }
                item {
                    GetTileWidget(hint = "Flight Date & Time", value = "2022-05-24 3:15 PM")
                }
                item {
                    GetTileWidget(hint = "Booking Reference", value = "B2022050001")
                }
                item {
                    GetTileWidget(hint = "Cargo Type", value = "General")
                }
                item {
                    GetTileWidget(hint = "Package Dimensions (L x W x H)", value = "15 12 20")
                }
                item {
                    GetTileWidget(hint = "Package Weight (Kg)", value = "5")
                }
                item {
                    GetTileWidget(hint = "AWB Number", value = "2324334730")
                }
                item {
                    GetTileWidgetWithIcon(hint = "View Cargo Manifest")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.3f)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.DashboardScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = hintLightGray)
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    navController.navigate(Screen.UpdateBookingScreen.route)
                }, colors = ButtonDefaults.buttonColors(backgroundColor = BlueLight2)) {
                    Text(
                        text = "Update",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {  navController.navigate(Screen.DashboardScreen.route) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BlueLight2)
                ) {
                    Text(
                        text = "Accept",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
            }
        }

    }
}

@Composable
fun GetTileWidget(hint: String, value: String) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(all = 16.dp)) {
        Text(text = hint, style = MaterialTheme.typography.body2.copy(color = hintLightGray))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = value, style = MaterialTheme.typography.body2)
    }
}

@Composable
fun GetTileWidgetWithIcon(hint: String) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(all = 16.dp)) {
        Text(text = hint, style = MaterialTheme.typography.body2.copy(color = hintLightGray))
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.ic_pdf_icon),
            contentDescription = "pdf",
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp),
        )

    }
}