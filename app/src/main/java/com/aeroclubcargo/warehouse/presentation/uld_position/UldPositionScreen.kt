package com.aeroclubcargo.warehouse.presentation.uld_position


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.CommonTextField
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
import com.aeroclubcargo.warehouse.presentation.uld_master.ULDMasterVIewModel
import com.aeroclubcargo.warehouse.theme.Black
import com.aeroclubcargo.warehouse.theme.BlueLight
import com.aeroclubcargo.warehouse.theme.BlueLight2
import com.aeroclubcargo.warehouse.theme.BlueLight4
import com.aeroclubcargo.warehouse.theme.Gray2
import com.aeroclubcargo.warehouse.theme.Gray5
import com.aeroclubcargo.warehouse.theme.Green
import com.aeroclubcargo.warehouse.theme.hintLightGray
import com.aeroclubcargo.warehouse.utils.ListState
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

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
        GetULTMasterUI(viewModel)
    }
}


@Composable
fun GetULTMasterUI(viewModel: UldPositionViewModel) {
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
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(text = "Add ULD Position", style = MaterialTheme.typography.subtitle1)

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
//    val noOfPackagesValue =  viewModel.uldNumber.collectAsState()
    val flightScheduleValue = viewModel.flightScheduleValue.collectAsState()

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
        ULDDataTable(viewModel)
    }
}

@Composable
fun ULDDataTable(viewModel: UldPositionViewModel) {
    val todoListState = viewModel.assignedUldListFlow.collectAsState()
    val headerStyle = MaterialTheme.typography.body2.copy(color = Black)
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

                        var expanded = remember  { mutableStateOf(false) }
                        var selectedOption = remember  { mutableStateOf("Option 1") }

                        // Options for the dropdown
                        val options = listOf("Option 1", "Option 2", "Option 3")
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            modifier = Modifier
                                .padding(16.dp)
                                .wrapContentWidth()
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(onClick = {
                                    selectedOption.value = option
                                    expanded.value = false
                                }) {
                                    Text(text = option)
                                }
                            }
                        }
                        TextField(value = selectedOption.value,
                            onValueChange = {
                            },
                            modifier = Modifier
                                .weight(0.7f)
                                .height(55.dp)
                                .padding(4.dp)
                                .border(1.dp, Gray2, shape = MaterialTheme.shapes.small)
                            .clickable {
                                    expanded.value = true
                                }
                            ,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            textStyle = MaterialTheme.typography.subtitle2.copy(fontSize = 14.sp,),
                            trailingIcon =  @Composable {
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
                                    .size(20.dp).weight(0.3f)
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