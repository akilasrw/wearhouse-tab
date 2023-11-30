package com.aeroclubcargo.warehouse.presentation.add_lir_data.lir_detail

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.presentation.recieved_cargo_details.HeaderTile
import com.aeroclubcargo.warehouse.theme.Black
import com.aeroclubcargo.warehouse.theme.BlueLight
import com.aeroclubcargo.warehouse.theme.BlueLight4
import com.aeroclubcargo.warehouse.theme.Gray5
import com.aeroclubcargo.warehouse.theme.Green
import com.aeroclubcargo.warehouse.theme.LightBlueBorderColor

// TODO add flight schedule details
// lir depends on aircraft type
// flight schedule ekata adala flight position tika
// Search based on ULD position

@Composable
fun LirDetailScreen(navController: NavController, scheduleModel: FlightScheduleModel?, viewModel: LirDetailsViewModel = hiltViewModel()){
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        AddLirDataMainScreen(flightScheduleValue = scheduleModel, viewModel = viewModel)
    }
    LaunchedEffect(key1 = true ){
        viewModel.getCargoPositionsDetails(scheduleModel)
    }
}

val column0Weight = .04f
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
    val cargoPositionsDetailListFlow = viewModel.cargoPositionsDetailListFlow.collectAsState()

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
                    .padding(top = 2.dp, bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                    ){
                    Row (modifier = Modifier.fillMaxWidth(0.8f)){
                        Text("Total weight")
                        Text(": 6/20 KG")
                        Text("|", modifier = Modifier.padding(start = 10.dp, end = 16.dp))
                        Text("Total Volume")
                        Text(": 30/50 m3")
                    }
                   Row (modifier = Modifier.fillMaxWidth(0.2f)){
                       IconButton(
                           modifier = Modifier
                               .fillMaxWidth(),
                           onClick = {

                           },
                       ) {
                           Icon(
                               painter = painterResource(R.drawable.baseline_save_24),
                               contentDescription = "review",
                               modifier = Modifier
                                   .size(30.dp),
                               tint = BlueLight
                           )
                       }
                   }
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
                            TableCell(text = "ULD Number", weight = column0Weight, style = headerStyle)
                            TableCell(text = "LIR Weight", weight = column2Weight, style = headerStyle)
                            TableCell(text = "LIR Volume", weight = column3Weight, style = headerStyle)
                            TableCell(text = "LIR Height", weight = column1Weight, style = headerStyle)
                        }
                    }
                    // data

                    items(cargoPositionsDetailListFlow.value.size) { index->
                        var cargoItem = cargoPositionsDetailListFlow.value[index]
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TableCell(text = "${cargoItem.name}",column0Weight)
                            TableCellEditText(text = "${cargoItem.maxWeight}",column2Weight)
                            TableCellEditText(text = "${cargoItem.maxVolume}",column2Weight)
                            TableCellEditText(text = "${cargoItem.height}",column2Weight)
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
//            textStyle = style,
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = LightBlueBorderColor
//            ),
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



