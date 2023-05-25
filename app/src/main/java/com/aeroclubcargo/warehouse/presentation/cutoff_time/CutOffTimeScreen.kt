package com.aeroclubcargo.warehouse.presentation.cutoff_time

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.aeroclubcargo.warehouse.domain.model.CutOffTimeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun CutOffTimeScreen(navController: NavController, viewModel: CutOffTimeViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        GetCutOffTimeList(viewModel = viewModel, navController = navController)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GetCutOffTimeList(viewModel: CutOffTimeViewModel, navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val frFlightName = remember { FocusRequester() }
    val frFlightDate = remember { FocusRequester() }

    val flightValue = viewModel.flightNameValue.collectAsState()
    val isLoading  = viewModel.isLoading.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    ) {
        Text(text = "Update Cut Off Time")
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Row(modifier = Modifier
                .height(60.dp)
                .align(alignment = Alignment.Start)
                .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ){
                OutlinedTextField(
                    value = flightValue.value,
                    onValueChange = viewModel::onFlightNameChange,
                    label = {
                        Text(
                            text = "Flight Number",
                            style = MaterialTheme.typography.body2.copy(color = Gray1)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = {
                        Text(
                            text = "Flight Number",
                            style = MaterialTheme.typography.body2.copy(color = Gray1)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .width(200.dp)
                        .focusRequester(focusRequester = frFlightName),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Gray2
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            CoroutineScope(Dispatchers.Default).launch {
                                keyboardController?.hide()
                                delay(400)
                                frFlightDate.requestFocus()
                            }
                        }
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    value = flightValue.value,
                    onValueChange = viewModel::onFlightNameChange,
                    label = {
                        Text(
                            text = "Departure Date",
                            style = MaterialTheme.typography.body2.copy(color = Gray1)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = {
                        Text(
                            text = "Departure Date",
                            style = MaterialTheme.typography.body2.copy(color = Gray1)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .width(200.dp)
                        .focusRequester(focusRequester = frFlightDate),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Gray2
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
//                            CoroutineScope(Dispatchers.Default).launch {
//                                keyboardController?.hide()
//                                delay(400)
//                                focusRequesterPassword.requestFocus()
//                            }
                        }
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier.wrapContentSize(align = Alignment.Center),
                    onClick = { /*** TODO **/ },
                ) {
                    Text(text = "Find", style = TextStyle(color = Color.White))
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
                CutOffTimeTable(viewModel= viewModel)
            }
        }
    }
}

@Composable
fun CutOffTimeTable(viewModel: CutOffTimeViewModel) {
    val column1Weight = .1f
    val column2Weight = .1f
    val column3Weight = .1f
    val column4Weight = .1f
    val column5Weight = .1f
    val column6Weight = .1f
    val column7Weight = .1f
    val column8Weight = .125f
    val column9Weight = .125f
    val column10Weight = .09f

//    var showDialog by remember { mutableStateOf(false) }
//    if(showDialog){
//        TimePickerDialog(
//            onConfirm = {
//
//            },
//            onCancel = {
//                showDialog = false
//            },
//            title = "Please select time",
//            toggle = {
//
//            },
//            content = {
//                Text(text = "TEAS")
//            },
//        )
//    }

    val headerStyle = MaterialTheme.typography.body2.copy(color = hintLightGray)
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // header
        item {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                TableCell(text = "Flight No.", weight = column1Weight, style = headerStyle)
                TableCell(text = "Dep.Date", weight = column2Weight, style = headerStyle)
                TableCell(text = "Dep.Time", weight = column3Weight, style = headerStyle)
                TableCell(text = "Cut Off Time", weight = column4Weight, style = headerStyle)
                TableCell(text = "Origin", weight = column5Weight, style = headerStyle)
                TableCell(text = "Dest", weight = column6Weight, style = headerStyle)
                TableCell(text = "Aircraft Type", weight = column7Weight, style = headerStyle)
                TableCell(text = "Tot.Weight(KG)", weight = column8Weight, style = headerStyle)
                TableCell(text = "Tot.Volume(m3)", weight = column9Weight, style = headerStyle)
                TableCell(text = "Actions", weight = column10Weight, style = headerStyle)
            }
        }

         val _uiState = MutableStateFlow(viewModel.cutOffTimeList.value)
        val uiState: StateFlow<List<CutOffTimeModel>?> = _uiState.asStateFlow()


        // data
        if (viewModel.cutOffTimeList.value != null)
            items(viewModel.cutOffTimeList.value!!) {booking->
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TableCell(text = "${booking.flightNumber}", weight = column1Weight)
                    TableCell(text = "${booking.scheduledDepartureDateTime}", weight = column2Weight)
                    TableCell(text = "${booking.scheduledDepartureDateTime}", weight = column3Weight)
                    TableCell(text = "${booking.cutoffTimeMin}", weight = column4Weight)
                    TableCell(text = "${booking.originAirportCode}", weight = column5Weight)
                    TableCell(text = "${booking.destinationAirportCode}", weight = column6Weight)
                    TableCell(text = booking.aircraftRegNo ?:"N/A", weight = column7Weight)
                    TableCell(text = (booking.totalBookedWeight.toString()), weight = column8Weight)
                    TableCell(text = (booking.totalBookedVolume.toString()), weight = column9Weight)
                    IconButton(
                        onClick = {
//                            showDialog = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit_icon),
                            contentDescription = "edit",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(3.dp),
                            tint = BlueLight
                        )
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
        textAlign = TextAlign.Center
    )
}



