package com.aeroclubcargo.warehouse.presentation.add_lir_data.lir_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.presentation.recieved_cargo_details.HeaderTile
import com.aeroclubcargo.warehouse.theme.Black
import com.aeroclubcargo.warehouse.theme.BlueLight
import com.aeroclubcargo.warehouse.theme.BlueLight4
import com.aeroclubcargo.warehouse.theme.Gray5
import com.aeroclubcargo.warehouse.theme.Green
import com.aeroclubcargo.warehouse.theme.LightBlueBorderColor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
// TODO add flight schedule details
// lir depends on aircraft type
//flight schedule ekata adala flight position tika
// Search based on ULD position
// max volume
// remove ULD position
// add button for each row.
// summary total weight
@Composable
fun LirDetailScreen(navController: NavController, scheduleModel: FlightScheduleModel?, viewModel: LirDetailsViewModel = hiltViewModel()){
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        AddLirDataMainScreen(flightScheduleValue = scheduleModel, viewModel = viewModel)
    }
}

val column0Weight = .035f
val column1Weight = .05f
val column2Weight = .1f
val column3Weight = .12f
val column4Weight = .1f
val column5Weight = .1f

//@Preview(device = Devices.AUTOMOTIVE_1024p,)
@Composable
fun AddLirDataMainScreen(
    flightScheduleValue: FlightScheduleModel?, viewModel: LirDetailsViewModel
){
    val headerStyle = MaterialTheme.typography.body2.copy(color = Black)
    val flightScheduleList = viewModel.flightScheduleListFlow.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp)) {
        Text(text = "LIR Data")
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White), contentAlignment = Alignment.Center
        ) {
            Column {
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
                            description = flightScheduleValue?.flightNumber?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dept. Date",
                            description =  flightScheduleValue?.scheduledDepartureDateTime?.split("T")?.first() ?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dept. Time",
                            description = flightScheduleValue?.scheduledDepartureDateTime?.split("T")?.last()?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Cut Off Time",
                            description =flightScheduleValue?.cutoffTime?.split("T")?.last() ?: "-"
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ORIG",
                            description = flightScheduleValue?.originAirportCode?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1.3f)

                    ) {
                        HeaderTile(
                            title = "DEST",
                            description = flightScheduleValue?.destinationAirportCode?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Act Type",
                            description = flightScheduleValue?.aircraftSubTypeName?: "-",
                            textColor = Green
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))

                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp)){
                    Text("Total weight")
                    Text(": 6/20 KG")
                    Text("|", modifier = Modifier.padding(start = 10.dp, end = 16.dp))
                    Text("Total Volume")
                    Text(": 30/50 m3")
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
                            TableCell(text = "ULD", weight = column0Weight, style = headerStyle)
                            TableCell(text = "LIR Weight", weight = column2Weight, style = headerStyle)
                            TableCell(text = "LIR Volume", weight = column3Weight, style = headerStyle)
                            TableCell(text = "ULD Number", weight = column4Weight, style = headerStyle)
                            TableCell(text = "Position Max Weight", weight = column1Weight, style = headerStyle)
                            TableCell(text = "Action", weight = column1Weight, style = headerStyle)
                        }
                    }
                    // data
                    items(3) { item->
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TableCell(text = "1", weight =  column0Weight)
                            TableCellEditText(text = "2",column2Weight)
                            TableCellEditText(text = "2",column2Weight)
                            TableCellEditText(text = "2",column2Weight)
                            TableCell(text = "20", weight =  column1Weight)
                            IconButton(
                                modifier = Modifier.weight(column1Weight),
                                onClick = {

                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add),
                                    contentDescription = "review",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(3.dp),
                                    tint = BlueLight
                                )
                            }
                        }
                    }
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


@Composable
fun RowScope.TableCellEditText(
    text: String,
    weight: Float,
    style: TextStyle = MaterialTheme.typography.body2,
) {
    TextField(value = text,
            onValueChange = {

            },
            modifier = Modifier
                .weight(weight)
                .padding(8.dp),
            textStyle = style,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = LightBlueBorderColor
            ),
            trailingIcon =  @Composable { IconButton(
                modifier = Modifier.size(25.dp),
                onClick = {

            }) {
                Icon(
                    modifier = Modifier.size(size = 20.dp),
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "close",
                    tint = BlueLight4
                )
            }
            }
        )

}



