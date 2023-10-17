package com.aeroclubcargo.warehouse.presentation.add_lir_data.lir_detail

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.add_lir_data.schedule_list.GetCutOffTimeList
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar

@Composable
fun LirDetailScreen(navController: NavController, scheduleModel: FlightScheduleModel?, viewModel: LirDetailsViewModel = hiltViewModel()){
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
//        GetCutOffTimeList(viewModel, navController)
    }
}