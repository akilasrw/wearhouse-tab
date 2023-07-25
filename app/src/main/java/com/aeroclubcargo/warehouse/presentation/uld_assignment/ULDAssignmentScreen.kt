package com.aeroclubcargo.warehouse.presentation.uld_assignment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.*
import com.aeroclubcargo.warehouse.utils.toDurationTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun ULDAssignmentScreen(navController: NavController, scheduleModel: FlightScheduleModel?, viewModel: ULDAssignmentViewModel = hiltViewModel()){
    viewModel.setFlightSchedule(scheduleModel)
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        GetCutOffTimeList(viewModel, navController)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GetCutOffTimeList(
    viewModel: ULDAssignmentViewModel,
    navController: NavController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val mContext = LocalContext.current

    val flightScheduleValue = viewModel.flightScheduleValue.collectAsState()

    val isLoading  = viewModel.isLoading.collectAsState()
    var calendar = Calendar.getInstance()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    ) {
        Text(text = "ULD Assignment")
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
                Row(modifier = Modifier
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Flight No",
                            desctiption = flightScheduleValue.value?.flightNumber?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dept. Date",
                            desctiption =  flightScheduleValue.value?.scheduledDepartureDateTime?.split("T")?.first() ?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dept. Time",
                            desctiption = flightScheduleValue.value?.scheduledDepartureDateTime?.split("T")?.last()?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Cut Off Time",
                            desctiption =flightScheduleValue.value?.cutoffTime?.split("T")?.last() ?: "-"
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ORIG",
                            desctiption = flightScheduleValue.value?.originAirportCode?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "DEST",
                            desctiption = flightScheduleValue.value?.destinationAirportCode?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Act Type",
                            desctiption = flightScheduleValue.value?.aircraftSubTypeName?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ULD Position",
                            desctiption =  flightScheduleValue.value?.uldPositionCount.toString()?: "0"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ULD Count",
                            desctiption =  flightScheduleValue.value?.uldCount.toString()?: "0",
                            textColor = Green
                        )
                    }

                }
                if (isLoading.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Spacer(modifier = Modifier.width(6.dp))
                    Divider(color = Gray4)
                    Spacer(modifier = Modifier.width(6.dp))
                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Added ULD")
                        Button(onClick = {
                            /*TODO*/
                        }) {
                            Text(text = "Add ULD", style = TextStyle(color = Color.White))
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun HeaderTile(title:String,desctiption:String,textColor: Color =  Color.Black) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(BlueLight3)
        .border(1.dp, BlueLight5, RoundedCornerShape(5.dp))
        .padding(8.dp)
    ) {
        Column() {
            Text(text = title, style = MaterialTheme.typography.subtitle2.copy(color = Gray6, fontSize = 12.sp),)
            Text(text = desctiption,style = MaterialTheme.typography.subtitle1.copy(color = textColor,fontSize = 14.sp))
        }
    }
}

//
//val column1Weight = .1f
//val column2Weight = .12f
//val column3Weight = .12f
//val column4Weight = .1f
//val column5Weight = .1f
//val column6Weight = .1f
//val column7Weight = .1f
//val column8Weight = .125f
//val column9Weight = .125f
//val column10Weight = .09f
//
//@Composable
//fun FlightsTable(viewModel: ULDAssignmentViewModel) {
//    val mContext = LocalContext.current
//    val todoListState = viewModel.todoListFlow.collectAsState()
//    val headerStyle = MaterialTheme.typography.body2.copy(color = hintLightGray)
//    val showAlert = remember { mutableStateOf(false) }
//    val context = LocalContext.current
//
//    if (showAlert.value) {
//        AlertDialog(
//            onDismissRequest = { showAlert.value = false },
//            title = { Text("Error") },
//            text = { Text("cut-off time should be Below the scheduled time") },
//            confirmButton = {
//                Button(
//                    onClick = { showAlert.value = false }
//                ) {
//                    Text(stringResource(R.string.ok))
//                }
//            },
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//    LazyColumn(
//        Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // header
//        item {
//            Row(
//                Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
//            ) {
//                TableCell(text = "Flight No.", weight = column1Weight, style = headerStyle)
//                TableCell(text = "Dep.Date", weight = column2Weight, style = headerStyle)
//                TableCell(text = "Dep.Time", weight = column3Weight, style = headerStyle)
//                TableCell(text = "Cut Off Time", weight = column4Weight, style = headerStyle)
//                TableCell(text = "Origin", weight = column5Weight, style = headerStyle)
//                TableCell(text = "Dest", weight = column6Weight, style = headerStyle)
//                TableCell(text = "Aircraft Type", weight = column7Weight, style = headerStyle)
//                TableCell(text = "ULD\nPositions", weight = column8Weight, style = headerStyle)
//                TableCell(text = "ULD\nCount", weight = column9Weight, style = headerStyle)
//                TableCell(text = "Add ULD", weight = column10Weight, style = headerStyle)
//            }
//        }
//        // data
//        items(todoListState.value) {booking->
//            Row(
//                Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                TableCell(text = "${booking.flightNumber}", weight =  column1Weight)
//                TableCell(text = "${booking.scheduledDepartureDateTime?.split("T")?.first()}", weight =  column2Weight)
//                TableCell(text = "${booking.scheduledDepartureDateTime?.split("T")?.last()}", weight =  column3Weight)
//                TableCell(text = booking.cutoffTimeMin?.toDurationTime() ?: "-", weight =  column4Weight)
//                TableCell(text = "${booking.originAirportCode}", weight =  column5Weight)
//                TableCell(text = "${booking.destinationAirportCode}", weight =  column6Weight)
//                TableCell(text = booking.aircraftRegNo ?:"-", weight =  column7Weight)
//                TableCell(text = (booking.totalBookedWeight.toString()), weight =  column8Weight)
//                TableCell(text = (booking.totalBookedVolume.toString()), weight =  column9Weight)
//                IconButton(
//                    onClick = {
//                        //TODO
//                    }
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_add),
//                        contentDescription = "edit",
//                        modifier = Modifier
//                            .size(24.dp)
//                            .padding(3.dp),
//                        tint = BlueLight
//                    )
//                }
//
//            }
//        }
//    }
//}
//
//@Composable
//fun RowScope.TableCell(
//    text: String,
//    weight: Float,
//    style: TextStyle = MaterialTheme.typography.body2
//) {
//    Text(
//        text = text,
//        Modifier
//            .weight(weight)
//            .padding(8.dp),
//        style = style,
//        textAlign = TextAlign.Center
//    )
//}
//


