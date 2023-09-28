package com.aeroclubcargo.warehouse.presentation.receive_cargo

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.presentation.flight_schedule.FlightScheduleViewModel
import com.aeroclubcargo.warehouse.theme.Black
import com.aeroclubcargo.warehouse.theme.BlueLight
import com.aeroclubcargo.warehouse.theme.Gray1
import com.aeroclubcargo.warehouse.theme.Gray2
import com.aeroclubcargo.warehouse.theme.Gray5
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun ReceiveCargoListScreen(navController: NavController,
                           viewModel : ReceiveCargoListViewModel = hiltViewModel()){
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        GetCutOffTimeList(viewModel, navController)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GetCutOffTimeList(
    viewModel: ReceiveCargoListViewModel,
    navController: NavController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val mContext = LocalContext.current

    val frFlightFromDate = remember { FocusRequester() }
    val frFlightToDate = remember { FocusRequester() }
    val filterButton = remember { FocusRequester() }

    val dateFromValue = viewModel.flightDateFromValue.collectAsState()
    val dateToValue = viewModel.flightDateToValue.collectAsState()

    val isLoading  = viewModel.isLoading.collectAsState()
    var calendar = Calendar.getInstance()

    val datePickerDialogFrom = DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            viewModel.onFlightFromDateChange("$year-${1+month}-$dayOfMonth")
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val datePickerDialogTo = DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            viewModel.onFlightToDateChange("$year-${1+month}-$dayOfMonth")
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    ) {
        Text(text = stringResource(id = R.string.receive_cargo))
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Row(modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ){
                OutlinedTextField(
                    value = dateFromValue.value,
                    enabled = false,
                    onValueChange = viewModel::onFlightFromDateChange,
                    label = {
                        Text(
                            text = "Select Date From",
                            style = MaterialTheme.typography.body2.copy(color = Gray1, fontSize = 10.sp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = {
                        Text(
                            text = "Select Date From",
                            style = MaterialTheme.typography.body2.copy(color = Gray1, fontSize = 10.sp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .width(200.dp)
                        .focusRequester(focusRequester = frFlightFromDate)
                        .clickable(onClick = {
                            datePickerDialogFrom.show()
                        }),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Gray2
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            CoroutineScope(Dispatchers.Default).launch {
                                keyboardController?.hide()
                                delay(400)
                                frFlightToDate.requestFocus()
                            }
                        }
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    value = dateToValue.value,
                    enabled = false,
                    onValueChange = viewModel::onFlightFromDateChange,
                    label = {
                        Text(
                            text = "Select Date To",
                            style = MaterialTheme.typography.body2.copy(color = Gray1, fontSize = 10.sp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = {
                        Text(
                            text = "Select Date To",
                            style = MaterialTheme.typography.body2.copy(color = Gray1, fontSize = 10.sp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .width(200.dp)
                        .focusRequester(focusRequester = frFlightFromDate)
                        .clickable(onClick = {
                            datePickerDialogTo.show()
                        }),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Gray2
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            CoroutineScope(Dispatchers.Default).launch {
                                keyboardController?.hide()
                                delay(400)
                                filterButton.requestFocus()
                            }
                        }
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier
                        .focusRequester(focusRequester = filterButton)
                        .wrapContentSize(align = Alignment.Center),
                    onClick = {
                        viewModel.getScheduleList()
                        keyboardController?.hide()
                    },
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
                FlightsTable(viewModel= viewModel, navController = navController)
            }
        }
    }
}


val column1Weight = .1f
val column2Weight = .12f
val column3Weight = .12f
val column4Weight = .1f
val column5Weight = .1f
val column6Weight = .1f
val column7Weight = .1f
val column8Weight = .125f
val column9Weight = .125f
val column10Weight = .09f

@Composable
fun FlightsTable(viewModel: ReceiveCargoListViewModel, navController: NavController) {
    val mContext = LocalContext.current
    val flightScheduleList = viewModel.flightScheduleListFlow.collectAsState()
    val headerStyle = MaterialTheme.typography.body2.copy(color = Black)
    val showAlert = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            title = { Text("Error") },
            text = { Text("cut-off time should be Below the scheduled time") },
            confirmButton = {
                Button(
                    onClick = { showAlert.value = false }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
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
                TableCell(text = "Flight No.", weight = column1Weight, style = headerStyle)
                TableCell(text = "Dep.Date", weight = column2Weight, style = headerStyle)
                TableCell(text = "Dep.Time", weight = column3Weight, style = headerStyle)
                TableCell(text = "Cut Off Time", weight = column4Weight, style = headerStyle)
                TableCell(text = "Origin", weight = column5Weight, style = headerStyle)
                TableCell(text = "Dest", weight = column6Weight, style = headerStyle)
                TableCell(text = "Aircraft Type", weight = column7Weight, style = headerStyle)
                TableCell(text = "Review", weight = column10Weight, style = headerStyle)
            }
        }
        // data
        items(flightScheduleList.value) { flightScheduleModel->
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TableCell(text = "${flightScheduleModel.flightNumber}", weight =  column1Weight)
                TableCell(text = "${flightScheduleModel.scheduledDepartureDateTime?.split("T")?.first()}", weight =  column2Weight)
                TableCell(text = "${flightScheduleModel.scheduledDepartureDateTime?.split("T")?.last()}", weight =  column3Weight)
                TableCell(text = flightScheduleModel.cutoffTime?.split("T")?.last() ?: "-", weight =  column4Weight)
                TableCell(text = "${flightScheduleModel.originAirportCode}", weight =  column5Weight)
                TableCell(text = "${flightScheduleModel.destinationAirportCode}", weight =  column6Weight)
                TableCell(text = flightScheduleModel.aircraftSubTypeName ?:"-", weight =  column7Weight)
                IconButton(
                    modifier = Modifier.weight(column10Weight),
                    onClick = {
//                    TODO    navController.navigate( // add route)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_rate_review_24),
                        contentDescription = "review",
                        modifier = Modifier
                            .size(36.dp)
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
