package com.aeroclubcargo.warehouse.presentation.uld_position


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.domain.model.CargoPositionVM
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.presentation.uld_assignment.HeaderTile
import com.aeroclubcargo.warehouse.presentation.uld_assignment.TableCell
import com.aeroclubcargo.warehouse.presentation.uld_assignment.column10Weight
import com.aeroclubcargo.warehouse.presentation.uld_assignment.column1Weight
import com.aeroclubcargo.warehouse.presentation.uld_assignment.column2Weight
import com.aeroclubcargo.warehouse.presentation.uld_assignment.column3Weight
import com.aeroclubcargo.warehouse.presentation.uld_assignment.column5Weight
import com.aeroclubcargo.warehouse.presentation.uld_assignment.column6Weight
import com.aeroclubcargo.warehouse.presentation.uld_assignment.column9Weight
import com.aeroclubcargo.warehouse.theme.Black
import com.aeroclubcargo.warehouse.theme.BlueLight2
import com.aeroclubcargo.warehouse.theme.Gray2
import com.aeroclubcargo.warehouse.theme.Gray5
import com.aeroclubcargo.warehouse.theme.Green
import com.aeroclubcargo.warehouse.theme.hintLightGray
import kotlinx.coroutines.launch

@Composable
fun ULDPositionScreen(
    navController: NavController,
    scheduleModel: FlightScheduleModel?,
    viewModel: UldPositionViewModel = hiltViewModel()
) {
    scheduleModel?.let { viewModel.setFlightSchedule(it) }
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        GetULTMasterUI(viewModel,navController)
    }
}


@Composable
fun GetULTMasterUI(viewModel: UldPositionViewModel, navController: NavController,) {

    val coroutineScope = rememberCoroutineScope()
    val showAlert = remember { mutableStateOf(false) }
    val alertTitle = remember { mutableStateOf("") }
    var alertMessage = remember {
        mutableStateOf("")
    }
    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            title = { Text(text = alertTitle.value) },
            text = { Text(alertMessage.value) },
            confirmButton = {
                Button(
                    onClick = {
                        showAlert.value = false
                        viewModel.getULDList()
                    }
                ) {
                    Text(stringResource(R.string.ok), style = TextStyle(color = Color.White))
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "Add ULD Position", style = MaterialTheme.typography.subtitle1)
            Row {
                Button(onClick = {
                    navController.navigate(Screen.PDFViewScreen.route)
                }, modifier = Modifier.padding(end = 10.dp) , colors = ButtonDefaults.buttonColors(backgroundColor = BlueLight2)) {
                    Text(
                        text = "Print",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
                Button(onClick = {
                    viewModel.saveALl(onComplete = { msg, error ->

                        if (!error.isNullOrEmpty()) {
                            coroutineScope.launch {
                                alertMessage.value = error
                                showAlert.value = true
                                alertTitle.value = "ULD Updated Error!"
                            }
                        }

                        if (!msg.isNullOrEmpty()) {
                            coroutineScope.launch {
                                alertMessage.value = msg
                                showAlert.value = true
                                alertTitle.value = "ULD Updated!"
                            }
                        }
                    })
                }, modifier = Modifier.padding(end = 10.dp) , colors = ButtonDefaults.buttonColors(backgroundColor = BlueLight2)) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
                Button(onClick = {
                    viewModel.clear(onComplete = { msg, error ->
                        if (!error.isNullOrEmpty()) {
                            coroutineScope.launch {
                                alertMessage.value = error
                                showAlert.value = true
                                alertTitle.value = "ULD Clear Error!"
                            }
                        }

                        if (!msg.isNullOrEmpty()) {
                            coroutineScope.launch {
                                alertMessage.value = msg
                                showAlert.value = true
                                alertTitle.value = "ULD Cleared!"
                            }
                        }
                    })
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                    Text(
                        text = "Clear",
                        style = MaterialTheme.typography.button.copy(color = BlueLight2)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            MainUIPanel(viewModel)
        }
    }
}

//@Preview(device = Devices.AUTOMOTIVE_1024p,)
@Composable
fun MainUIPanel(
    viewModel: UldPositionViewModel,
) {
    val flightScheduleValue = viewModel.flightScheduleValue.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.background(color = Color.White)) {
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "Flight No",
                    desctiption = flightScheduleValue.value?.flightNumber ?: "-"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "Dept. Date",
                    desctiption = flightScheduleValue.value?.scheduledDepartureDateTime?.split("T")
                        ?.first() ?: "-"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "Dept. Time",
                    desctiption = flightScheduleValue.value?.scheduledDepartureDateTime?.split("T")
                        ?.last() ?: "-"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "Cut Off Time",
                    desctiption = flightScheduleValue.value?.cutoffTime?.split("T")?.last() ?: "-"
                )
            }

            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "ORIG",
                    desctiption = flightScheduleValue.value?.originAirportCode ?: "-"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "DEST",
                    desctiption = flightScheduleValue.value?.destinationAirportCode ?: "-"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "Act Type",
                    desctiption = flightScheduleValue.value?.aircraftSubTypeName ?: "-"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "ULD Position",
                    desctiption = flightScheduleValue.value?.uldPositionCount.toString() ?: "0"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                HeaderTile(
                    title = "ULD Count",
                    desctiption = flightScheduleValue.value?.uldCount.toString() ?: "0",
                    textColor = Green
                )
            }

        }
        if (isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ULDDataTable(viewModel)
        }

    }
}

@Composable
fun ULDDataTable(viewModel: UldPositionViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val showAlert = remember { mutableStateOf(false) }
    val alertTitle = remember { mutableStateOf("") }
    var alertMessage = remember {
        mutableStateOf("")
    }
    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            title = { Text(text = alertTitle.value) },
            text = { Text(alertMessage.value) },
            confirmButton = {
                Button(
                    onClick = {
                        showAlert.value = false
                        viewModel.getULDList()
                    }
                ) {
                    Text(stringResource(R.string.ok), style = TextStyle(color = Color.White))
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

    val todoListState = viewModel.assignedUldListFlow.collectAsState()
    val headerStyle = MaterialTheme.typography.body2.copy(color = Black)

    viewModel.isLoading
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
                TableCell(text = "ULD number", weight = column2Weight, style = headerStyle)
                TableCell(text = "ULD Type", weight = column1Weight, style = headerStyle)
                TableCell(text = "Dimensions", weight = column8Weight, style = headerStyle)
                TableCell(text = "Max Weight", weight = column3Weight, style = headerStyle)
                TableCell(text = "Received Weight", weight = column10Weight, style = headerStyle)
                TableCell(text = "Max Volume", weight = column5Weight, style = headerStyle)
                TableCell(text = "Received Volume", weight = column6Weight, style = headerStyle)
                TableCell(text = "Actions", weight = column9Weight, style = headerStyle)
            }
        }
        // data
        if (todoListState.value != null) {
            items(todoListState.value!!) { uldModel ->
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    TableCell(text = "${uldModel.serialNumber}", weight = column2Weight)
                    TableCell(
                        text = "${Constants.getULDType(uldModel.uldType)}",
                        weight = column1Weight
                    )
                    TableCell(
                        text = "${uldModel.width} x ${uldModel.height}x ${uldModel.length}",
                        weight = column8Weight
                    )
                    TableCell(text = "${uldModel.maxWeight} Kg", weight = column3Weight)
                    TableCell(text = "${uldModel.weight} kg", weight = column10Weight)
                    TableCell(text = "${uldModel.maxVolume} m3", weight = column5Weight)
                    TableCell(
                        text = "${uldModel.volume} m3",
                        weight = column6Weight
                    )
                    Row(
                        Modifier.weight(column9Weight),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        var expanded = remember { mutableStateOf(false) }
                        var selectedOption =
                            remember { mutableStateOf<CargoPositionVM?>(uldModel.cargoPositionVM) }

                        // Options for the dropdown
                        val cargoPositionListFlow = viewModel.cargoPositionListFlow.collectAsState()
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            modifier = Modifier
                                .padding(16.dp)
                                .wrapContentWidth()
                        ) {
                            cargoPositionListFlow.value?.filter { !it.isAssigned }?.forEach { position ->
                                DropdownMenuItem(onClick = {
                                    selectedOption.value = position
                                    expanded.value = false
                                    viewModel.addPosition(uldModel, position)
                                }) {
                                    Row(modifier = Modifier.size(20.dp)) {
                                        Text(text = position.name + " ")
                                        if (selectedOption.value?.id == position.id)
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_accepted),
                                                contentDescription = "",
                                            )
                                    }

                                }
                            }
                        }
                        TextField(
                            value = selectedOption.value?.name ?: "-",
                            onValueChange = {
                            },
                            modifier = Modifier
                                .weight(0.7f)
                                .height(55.dp)
                                .padding(4.dp)
                                .border(1.dp, Gray2, shape = MaterialTheme.shapes.small)
                                .clickable {
                                    expanded.value = true
                                },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            textStyle = MaterialTheme.typography.subtitle2.copy(fontSize = 14.sp),
                            trailingIcon = @Composable {
                                IconButton(
                                    onClick = {
                                        expanded.value = true
                                    }) {
                                    Icon(
                                        modifier = Modifier.size(size = 18.dp),
                                        imageVector = Icons.Rounded.KeyboardArrowDown,
                                        contentDescription = "",
                                        tint = Gray2
                                    )
                                }
                            },
                            enabled = false
                        )
                        IconButton(
                            modifier = Modifier.size(20.dp),
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_accepted),
                                contentDescription = "edit",
                                modifier = Modifier
                                    .size(20.dp)
                                    .weight(0.3f)
                                    .padding(1.dp),
                                tint = hintLightGray // Green
                            )
                        }

                    }
                }
            }
        }

    }
}

@Composable
fun ListItem(item: String) {
    Text(text = item, color = Color.Black)
}