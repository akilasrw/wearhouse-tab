package com.aeroclubcargo.warehouse.presentation.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
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
import com.aeroclubcargo.warehouse.domain.model.Booking
import com.aeroclubcargo.warehouse.presentation.dashboard.components.GetTopBar
import com.aeroclubcargo.warehouse.theme.BlueLight
import com.aeroclubcargo.warehouse.theme.Green
import com.aeroclubcargo.warehouse.theme.hintLightGray

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashBoardViewModel = hiltViewModel()) {

    val userModel = viewModel.userValue.collectAsState()

    Scaffold(topBar = {
        GetTopBar(userName = "${userModel.value?.firstName} ${userModel.value?.lastName}", navController = navController)
    }) {
        GetDashboardMainUI()
    }
}


//@Preview(device = Devices.AUTOMOTIVE_1024p)
@Composable
fun GetDashboardMainUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(16.dp))
            GetTileButton(id = R.drawable.ic_scan, text = "Verify Booking", onClick = {

            })
            Spacer(modifier = Modifier.width(8.dp))
            GetTileButton(id = R.drawable.ic_accepted, text = "Accepted Cargo", onClick = {

            })
            Spacer(modifier = Modifier.width(8.dp))
            GetTileButton(
                id = R.drawable.ic_document,
                text = "Special Package Handling",
                onClick = {

                })
            Spacer(modifier = Modifier.width(8.dp))
            GetTileButton(id = R.drawable.ic_flight_schedule, text = "Flight Schedule", onClick = {

            })
            Spacer(modifier = Modifier.width(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        RecentCargoBookingPanel()
    }
}

@Composable
fun RecentCargoBookingPanel() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Accepted Cargo Bookings",
                style = MaterialTheme.typography.subtitle2.copy(color = Color.Black)
            )
            TextButton(onClick = { /*TODO*/ }) {
                Text("View All", style = MaterialTheme.typography.button)
            }
        }
        Card(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(), backgroundColor = Color.White
        ) {
            TableScreen()
        }
    }
}

@Composable
fun TableScreen() {
    val tableData = (1..10).mapIndexed { index, item ->
        index to Booking(
            flightNo = "FL 300",
            serialNo = "CA1547214562",
            status = "Active",
            dimensions = "5' x 10'",
            dateAndTime = "2/24/2022 13:15 PM",
            weight = "20 Kg"
        )
    }
    val column1Weight = .15f
    val column2Weight = .1f
    val column3Weight = .1f
    val column4Weight = .08f
    val column5Weight = .08f
    val column6Weight = .2f
    val headerStyle = MaterialTheme.typography.body1.copy(color = hintLightGray)
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // header
        item {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                TableCell(text = "Serial No.", weight = column1Weight, style = headerStyle)
                TableCell(text = "Flight No.", weight = column2Weight, style = headerStyle)
                TableCell(text = "Dimensions", weight = column3Weight, style = headerStyle)
                TableCell(text = "Weight", weight = column4Weight, style = headerStyle)
                TableCell(text = "Status", weight = column5Weight, style = headerStyle)
                TableCell(text = "Date and Time", weight = column6Weight, style = headerStyle)
            }
        }
        // data
        items(tableData) {
            val (id, booking) = it
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                TableCell(text = booking.serialNo, weight = column1Weight)
                TableCell(text = booking.flightNo, weight = column2Weight)
                TableCell(text = booking.dimensions, weight = column3Weight)
                TableCell(text = booking.weight, weight = column4Weight)
                TableStatusButton(text = booking.status, weight = column5Weight)
                TableCell(text = booking.dateAndTime, weight = column6Weight)
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
fun RowScope.TableStatusButton(
    text: String,
    weight: Float,
    style: TextStyle = MaterialTheme.typography.body2
) {
    Surface(
        modifier = Modifier
            .weight(weight)
            .padding(top = 4.dp, start = 8.dp, end = 8.dp),
        elevation = 8.dp,
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = Green),
        color = MaterialTheme.colors.onSecondary
    ) {
        Text(
            text = text,
            Modifier
                .padding(8.dp),
            textAlign = TextAlign.Center,
            style = style.copy(color= Green)
        )
    }

}

@Composable
fun GetTileButton(@DrawableRes id: Int, text: String, onClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier
            .wrapContentWidth()
            .defaultMinSize(minWidth = 200.dp, minHeight = 160.dp)
            .background(color = Color.White),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = {
            onClick()
        },
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id),
                contentDescription = "text",
                modifier = Modifier
                    .size(60.dp)
                    .padding(4.dp),
                tint = BlueLight
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.primary)
            )
        }
    }
}
