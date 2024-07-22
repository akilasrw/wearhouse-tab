package com.aeroclubcargo.warehouse.presentation.verify_booking

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.Constants.getPackageItemCategory
import com.aeroclubcargo.warehouse.domain.model.PackageLineItem
import com.aeroclubcargo.warehouse.presentation.components.ProgressIndicatorDialog
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.*
import com.aeroclubcargo.warehouse.utils.toDateTimeDisplayFormat
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun VerifyBookingScreen(
    navController: NavController,
    bookingId: String? = null,
    viewModel: VerifyBookingViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val showDialog = remember { mutableStateOf(false) }
    val rememberScroll = rememberScrollState()

    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            content = {
                Column(
                    Modifier
                        .background(Color.White)
                        .fillMaxHeight(0.8f)) {
                    Row(modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Cargo Handling Instructions",
                            fontSize = 22.sp,
                            style = MaterialTheme.typography.subtitle1,
                        )
                        IconButton(onClick = { showDialog.value = false  }) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "notifications",
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                    Divider(modifier = Modifier.fillMaxWidth(), color = hintLightGray)
                    FlowRow(
                        modifier = Modifier
                            .verticalScroll(rememberScroll)
                            .padding(vertical = 16.dp, horizontal = 32.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.long_text), // TODO add proper text
                            fontSize = 18.sp,
                            softWrap = true,
                            textAlign = TextAlign.Start
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
        )
    }
    if(bookingId == null){
        navController.popBackStack()
    }
    LaunchedEffect(key1 = true) {
        viewModel.getPackageDetails(bookingId!!)
    }
    val updatPackageSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val loginState = viewModel.isLoading.observeAsState().value ?: false

    ProgressIndicatorDialog(loginState)

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
                                    value = viewModel.packageDetail.value?.bookingNumber ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "AWB number",
                                    value = viewModel.packageDetail.value?.awbNumber ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Flight Number",
                                    value = viewModel.packageDetail.value?.flightNumber ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Aircraft Type",
                                    value = viewModel.packageDetail.value?.aircraftSubTypeName ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "From to",
                                    value = "${viewModel.packageDetail.value?.origin} ${viewModel.packageDetail.value?.destination}"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Flight Date & Time",
                                    value = viewModel.packageDetail.value?.scheduledDepartureDateTime?.toDateTimeDisplayFormat(outputFormat = "MMM d, yyyy HH:mm a") ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Cut Off Time",
                                    value = viewModel.packageDetail.value?.cutoffTimeMin?.toString()
                                        ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Booking Date",
                                    value =  viewModel.packageDetail.value?.scheduledDepartureDateTime?.toDateTimeDisplayFormat(outputFormat = "yyyy/MM/dd")
                                        ?: "N/A"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "No.Rec. Pcs",
                                    value = "${viewModel.packageDetail.value?.numberOfRecBoxes}"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Total Rec. Weight(Kg)",
                                    value = "${viewModel.packageDetail.value?.totalRecWeight}"
                                )
                            }
                            item {
                                GetTileWidget(
                                    hint = "Total Rec. Volume(m3)",
                                    value = "${viewModel.packageDetail.value?.totalRecVolume}"
                                )
                            }
                            item {
                                Box(modifier = Modifier.clickable {
                                    coroutineScope.launch {
                                        showDialog.value = true
                                    }
                                }) {
                                    GetTileWidget(
                                        hint = "Cargo Handling Instructions",
                                        value = "View",
                                        textColor = BlueLight2
                                    )
                                }
                            }
                        }
                        PackageTable(
                            navController = navController,
                            viewModel = viewModel,
                            modalSheetState = updatPackageSheetState,
                            packages = viewModel.packageDetail.value?.packageItems ?: listOf()
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
    viewModel: VerifyBookingViewModel,
    modalSheetState: ModalBottomSheetState,
    packages : List<PackageLineItem>
) {
    val headerStyle =
        MaterialTheme.typography.body2.copy(color = Black, fontWeight = FontWeight.Bold)
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
                TableCell(text = "Package Type", weight = column2Weight, style = headerStyle)
                TableCell(
                    text = "Package Weight",
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
        items(packages) { lineItem ->
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TableCell(text = "${lineItem.packageRefNumber}", weight = column1Weight)
                TableCell(text = "${getPackageItemCategory(lineItem.packageItemType)}", weight = column2Weight)
                TableCell(text = "${lineItem.chargeableWeight}", weight = column3Weight)
                TableCell(text = "${lineItem.dimension}", weight = column4Weight)
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
                            else {
                                modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                viewModel.setLineItem(lineItem)
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = "edit",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }

                    Spacer(modifier = Modifier.width(2.dp))
                    IconButton(
                        onClick = {
                            if(lineItem.packageItemStatus != Constants.PackageItemStatus.Cargo_Received.ordinal) {
                                viewModel.acceptPackageItem(lineItem)
                            }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_ok),
                            contentDescription = "done",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = if (lineItem.packageItemStatus != Constants.PackageItemStatus.Cargo_Received.ordinal) BlueLight else Green
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    IconButton(onClick = { /* TODO ("implement delete package item") */ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "delete",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
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
fun GetTileWidget(hint: String, value: String, textColor: Color = Color.Black) {
    Surface(
        modifier = Modifier
            .padding(8.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(5),
        color = MaterialTheme.colors.onSurface,
    ) {
        Column(horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = hint,
                style = MaterialTheme.typography.body2
                    .copy(color = hintLightGray, fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = value, style = MaterialTheme.typography.body2
                .copy(fontSize = 12.sp, color = textColor))
        }
    }

}
