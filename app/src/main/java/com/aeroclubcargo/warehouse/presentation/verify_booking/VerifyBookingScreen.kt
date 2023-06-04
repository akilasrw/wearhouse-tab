package com.aeroclubcargo.warehouse.presentation.verify_booking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.*
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun VerifyBookingScreen(
    navController: NavController,
    viewModel: VerifyBookingViewModel = hiltViewModel()
) {
//    LaunchedEffect(key1 = true) {
//        viewModel.getPackageDetails()
////        viewModel.stateFlow
//    }

    val updatPackageSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    Scaffold(topBar = {
        GetTopBar(navController = navController)
    }) {
        UpdatePackageBottomSheet(
            viewModel = viewModel,
            modalSheetState = updatPackageSheetState,
            content = {
                Column(
                    modifier = Modifier
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
                            modifier = Modifier.wrapContentSize(),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
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
                                    value = viewModel.packageDetail.value?.awbTrackingNumber
                                        ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Booking Date",
                                    value = viewModel.packageDetail.value?.awbTrackingNumber
                                        ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "No.Rec. Pcs",
                                    value = viewModel.packageDetail.value?.awbTrackingNumber
                                        ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Total Rec. Weight(Kg)",
                                    value = viewModel.packageDetail.value?.awbTrackingNumber
                                        ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Total Rec. Volume(m3)",
                                    value = viewModel.packageDetail.value?.awbTrackingNumber
                                        ?: "N/A"
                                )
                            }
//                    item {
//                        GetTileWidgetWithIcon(hint = "View Cargo Manifest")
//                    }
                        }
                        PackageTable(
                            navController = navController,
                            viewModel = viewModel,
                            modalSheetState = updatPackageSheetState
                        )
                    }
                }
            }
        )


    }
}

val column1Weight = .2f
val column2Weight = .2f
val column3Weight = .2f
val column4Weight = .2f
val column5Weight = .2f

@ExperimentalMaterialApi
@Composable
fun PackageTable(
    navController: NavController,
    viewModel: VerifyBookingViewModel, modalSheetState: ModalBottomSheetState
) {
    val headerStyle =
        MaterialTheme.typography.body2.copy(color = Black, fontWeight = FontWeight.Bold)
    val todoListState = listOf("A", "B", "C")
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // header
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = Gray5),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                TableCell(
                    text = "Booking Reference",
                    weight = column1Weight,
                    style = headerStyle
                )
                TableCell(text = "Cargo Type", weight = column2Weight, style = headerStyle)
                TableCell(
                    text = "Package Weight(kg)",
                    weight = column3Weight,
                    style = headerStyle
                )
                TableCell(
                    text = "Package Dimensions(LxWxH)",
                    weight = column4Weight,
                    style = headerStyle
                )
                TableCell(text = "Action", weight = column5Weight, style = headerStyle)
            }
        }
        // data
        items(todoListState) { booking ->
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TableCell(text = "DG334523", weight = column1Weight)
                TableCell(text = "Dangours Good", weight = column2Weight)
                TableCell(text = "asda", weight = column3Weight)
                TableCell(text = "ada", weight = column4Weight)
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .weight(column5Weight),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (modalSheetState.isVisible)
                                modalSheetState.hide()
                            else
                                modalSheetState.animateTo(ModalBottomSheetValue.HalfExpanded)
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit_icon),
                            contentDescription = "edit",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))
                    // TODO booking status below 20 status
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_accepted),
                            contentDescription = "done",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_delete_outline_24),
                            contentDescription = "delete",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }

}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    style: TextStyle = MaterialTheme.typography.body2
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(8.dp),
        style = style,
        textAlign = TextAlign.Start
    )
}

@Composable
fun GetTileWidget(hint: String, value: String) {
    Surface(
        modifier = Modifier
            .padding(8.dp),

        elevation = 0.dp,
        shape = RoundedCornerShape(5),
        color = MaterialTheme.colors.onSurface
    ) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = hint,
                style = MaterialTheme.typography.body2.copy(color = hintLightGray, fontSize = 10.sp)
            )
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
            Text(
                text = hint,
                style = MaterialTheme.typography.body2.copy(color = hintLightGray, fontSize = 10.sp)
            )
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