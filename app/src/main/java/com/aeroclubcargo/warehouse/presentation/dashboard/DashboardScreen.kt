package com.aeroclubcargo.warehouse.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.presentation.dashboard.components.GetTopBar

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashBoardViewModel = hiltViewModel()) {

   val userModel =  viewModel.userValue.collectAsState()

    Scaffold(topBar = {
        GetTopBar(
            userName = "${userModel.value?.firstName} ${userModel.value?.lastName}",
            profileUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfHw%3D&w=1000&q=80",
        )
    }) {
        GetDashboardMainUI()
    }
}


//@Preview(device = Devices.AUTOMOTIVE_1024p)
@Composable
fun GetDashboardMainUI() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxHeight()) {

        }

    }
}