package com.aeroclubcargo.warehouse.presentation.recieved_cargo_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.*
import com.aeroclubcargo.warehouse.utils.toDateTimeDisplayFormat
import kotlinx.coroutines.launch


@Composable
fun ReceivedCargoDetailScreen(navController: NavController,
                              scheduleModel: FlightScheduleModel?,
                           viewModel : ReceivedCargoDetailVM = hiltViewModel()
){
    viewModel.setFlightSchedule(scheduleModel)
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        GetCutOffTimeList(
            viewModel,
            navController,
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun GetCutOffTimeList(
    viewModel: ReceivedCargoDetailVM,
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val mContext = LocalContext.current

    val flightScheduleValue = viewModel.flightScheduleValue.collectAsState()

    val isLoading  = viewModel.isLoading.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    ) {
        Text(text = "Received Cargo Details")
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
                            description = flightScheduleValue.value?.flightNumber?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dept. Date",
                            description =  flightScheduleValue.value?.scheduledDepartureDateTime?.split("T")?.first() ?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dept. Time",
                            description = flightScheduleValue.value?.scheduledDepartureDateTime?.split("T")?.last()?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Cut Off Time",
                            description =flightScheduleValue.value?.cutoffTime?.split("T")?.last() ?: "-"
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ORIG",
                            description = flightScheduleValue.value?.originAirportCode?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1.3f)

                    ) {
                        HeaderTile(
                            title = "DEST",
                            description = flightScheduleValue.value?.destinationAirportCode?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Act Type",
                            description = flightScheduleValue.value?.aircraftSubTypeName?: "-",
                            textColor = Green
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))

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
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Packages List")
                    }
                    FlightsTable(viewModel= viewModel)
                }
            }

        }
    }
}

@Composable
fun HeaderTile(title:String, description:String,textColor: Color =  Color.Black) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(BlueLight3)
        .border(1.dp, BlueLight5, RoundedCornerShape(5.dp))
        .padding(8.dp)
    ) {
        Column() {
            Text(text = title, style = MaterialTheme.typography.subtitle2.copy(color = Gray6, fontSize = 12.sp),)
            Text(text = description,style = MaterialTheme.typography.subtitle1.copy(color = textColor,fontSize = 14.sp))
        }
    }
}




@Composable
fun FlightsTable(viewModel: ReceivedCargoDetailVM) {
    val mContext = LocalContext.current
    val todoListState = viewModel.listOfPackages.collectAsState()
    val headerStyle = MaterialTheme.typography.body2.copy(color = Black)

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(
                start = 0.dp,
                end = 0.dp,
                top = 2.dp,
                bottom = 0.dp
            )
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
                TableCell(text = "Agent Name", weight =  .2f, style = headerStyle)
                TableCell(text = "AWB Number", weight =  .2f, style = headerStyle)
                TableCell(text = "No of Packages", weight =  .2f, style = headerStyle)
                TableCell(text = "Total Weight", weight =  .2f, style = headerStyle)
                TableCell(text = "Total Volume", weight =  .2f, style = headerStyle)
                TableCell(text = "Received Date and Time", weight =  .4f, style = headerStyle)
                TableCell(text = "Action", weight = column1Weight, style = headerStyle)
            }
        }
        
        // data
        if (todoListState.value!= null){
            items(todoListState.value!!) {uldModel->
                var isExpanded by remember { mutableStateOf(false) }
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = LightBlueBorderColor,
                            shape = RoundedCornerShape(8.dp) // Adjust the corner radius here
                        )
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(backgroundLightBlue)
                            .clickable { isExpanded = !isExpanded },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TableCell(text = uldModel.bookingAgent, weight =  .2f ,style = MaterialTheme.typography.button)
                        TableCell(text = uldModel.awbNumber, weight =  .2f ,style = MaterialTheme.typography.button)
                        TableCell(text = "${uldModel.numberOfBoxes}", weight =  .2f ,style = MaterialTheme.typography.button)
                        TableCell(text = "${uldModel.totalWeight}", weight =  .2f ,style = MaterialTheme.typography.button)
                        TableCell(text = "${uldModel.totalVolume}", weight =  .2f ,style = MaterialTheme.typography.button)
                        TableCell(text = "${uldModel.bookingDate.toDateTimeDisplayFormat(outputFormat = "MMM d, yyyy HH:mm a")}", weight =  .4f ,style = MaterialTheme.typography.button)
                        Row(modifier = Modifier
                            .padding(0.dp)
                            .weight(column1Weight),
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            IconButton(onClick = {
                                isExpanded = !isExpanded
                            }) {
                                Icon(
                                    painter = if(isExpanded) painterResource(R.drawable.baseline_keyboard_arrow_up_24) else painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                                    contentDescription = "delete",
                                    tint = BlueLight
                                )
                            }

                        }
                    }
                    if (isExpanded) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Gray7)
                                    .padding(8.dp)
                            ) {
                                SubTableCell(text = "Booking Reference", weight = .1f)
                                SubTableCell(text = "Cargo Type",weight = .1f)
                                SubTableCell(text = "Package Weight",weight = .1f)
                                SubTableCell(text = "Dimensions",weight = .1f)
                                SubTableCell(text = "No of Packages",weight = .1f)
                            }

                            // Sub-table Rows
                            listOf<Int>(1,3,4,5).forEach { subItem ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White)
                                        .padding(8.dp)
                                ) {
                                    SubTableCell(text = "DG-2340505",weight = .1f )
                                    SubTableCell(text = "General",weight = .1f)
                                    SubTableCell(text = "120kg",weight = .1f)
                                    SubTableCell(text = "0.5m3",weight = .1f)
                                    SubTableCell(text = "2",weight = .1f)
                                }
                            }
                        }
                    }
                }


            }
        }

    }
}

@Composable
fun RowScope.SubTableCell(
    text: String,
    weight: Float,
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(4.dp),
        style = MaterialTheme.typography.subtitle1.copy(fontSize = 13.sp),
        textAlign = TextAlign.Start
    )
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


