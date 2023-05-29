package com.aeroclubcargo.warehouse.presentation.verify_booking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Constants
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
//    LaunchedEffect(key1 = true) {
////        viewModel.getPackageDetails()
////        viewModel.stateFlow
//    }
    Scaffold(topBar = {
        GetTopBar(navController = navController)
    }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        ) {
            Text(text = "Verify Booking")
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {

                LazyVerticalGrid(
                    cells = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxHeight(fraction = 0.7f),
                    contentPadding = PaddingValues(horizontal =8.dp, vertical = 8.dp)
                ) {
                    item {
                        GetTileWidget(
                            hint = "Booking ID",
                            value = viewModel.packageDetail.value?.flightNumber ?: "N/A"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "AWB number",
                            value = viewModel.packageDetail.value?.flightDate?.split("T")
                                ?.get(0)
                                ?: "N/A"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "Flight Number",
                            value = viewModel.packageDetail.value?.bookingRefNumber ?: "N/A"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "Aircraft Type",
                            value = Constants.getCargoType(viewModel.packageDetail.value?.cargoPositionType)
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "From to",
                            value = "${viewModel.packageDetail.value?.length} ${viewModel.packageDetail.value?.width} ${viewModel.packageDetail.value?.height} (${viewModel.packageDetail.value?.volumeUnit})"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "Flight Date & Time",
                            value = "${viewModel.packageDetail.value?.weight} (${viewModel.packageDetail.value?.weightUnit})"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "Cut Off Time",
                            value = viewModel.packageDetail.value?.awbTrackingNumber ?: "N/A"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "Booking Date",
                            value = viewModel.packageDetail.value?.awbTrackingNumber ?: "N/A"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "No.Rec. Pcs",
                            value = viewModel.packageDetail.value?.awbTrackingNumber ?: "N/A"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "Total Rec. Weight(Kg)",
                            value = viewModel.packageDetail.value?.awbTrackingNumber ?: "N/A"
                        )
                    }
                    item {
                        GetTileWidget(
                            hint = "Total Rec. Volume(m3)",
                            value = viewModel.packageDetail.value?.awbTrackingNumber ?: "N/A"
                        )
                    }
//                    item {
//                        GetTileWidgetWithIcon(hint = "View Cargo Manifest")
//                    }
                }
                Spacer(modifier = Modifier.height(10.dp))


            }
        }
        

    }
}

@Composable
fun GetTileWidget(hint: String, value: String) {
    Surface(
        modifier = Modifier
            .padding(8.dp),

        elevation = 0.dp,
        shape = RoundedCornerShape(5),
        color = MaterialTheme.colors.onSurface
    ){
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(all = 16.dp)) {
            Text(text = hint, style = MaterialTheme.typography.body2.copy(color = hintLightGray, fontSize = 10.sp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = value, style = MaterialTheme.typography.body2.copy(fontSize = 10.sp))
        }
    }

}

@Composable
fun GetTileWidgetWithIcon(hint: String) {
    Surface(
        modifier = Modifier
            .padding(4.dp),

        elevation = 0.dp,
        shape = RoundedCornerShape(5),
        color = MaterialTheme.colors.onSurface
    ) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(all = 16.dp)) {
            Text(text = hint, style = MaterialTheme.typography.body2.copy(color = hintLightGray, fontSize = 10.sp))
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(R.drawable.ic_pdf_icon),
                contentDescription = "pdf",
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp),
            )

        }
    }
}