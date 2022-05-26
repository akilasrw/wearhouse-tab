package com.aeroclubcargo.warehouse.presentation.update_booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UpdateBookingScreen(
    navController: NavController,
    viewModel: UpdateBookingViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        GetTopBar(navController = navController)
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
                .background(color = Color.White)
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                modifier = Modifier.wrapContentHeight()
            ) {
                item {
                    GetTileWidget(hint = "Flight Number", value = "AC-101")
                }
                item {
                    GetTileWidget(hint = "Flight Date & Time", value = "2022-05-24 3:15 PM")
                }
                item {
                    GetTileWidget(hint = "Booking Reference", value = "B2022050001")
                }
                item {
                    GetTileWidget(hint = "AWB Number", value = "2324334730")
                }
                item {
                    GetTileWidgetWithIcon(hint = "View Cargo Manifest")
                }
            }
            Surface(modifier = Modifier.padding(16.dp),
                elevation = 0.dp,
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(width = 1.dp, color = Gray3),
                color = Gray3
                ) {
                DetailTable()
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.3f)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    navController.popBackStack()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = hintLightGray)) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        navController.navigate(Screen.DashboardScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BlueLight2)
                ) {
                    Text(
                        text = "Update & Accept",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun DetailTable() {
    var ctExpanded by remember { mutableStateOf(false) }
    val ctOptions = listOf("5,5,5", "4,4,4",)
    var ctSelectedOptionText by remember { mutableStateOf(ctOptions[0]) }

    var pdExpanded by remember { mutableStateOf(false) }
    val pdOptions = listOf("Custom", "Custom 2", "Custom 3", "Custom 4", "Custom 5")
    var pdSelectedOptionText by remember { mutableStateOf(ctOptions[0]) }

    var length by rememberSaveable { mutableStateOf("5") }
    var width by rememberSaveable { mutableStateOf("5") }
    var height by rememberSaveable { mutableStateOf("10") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {
            ExposedDropdownMenuBox(
                expanded = ctExpanded,
                onExpandedChange = {
                    ctExpanded = !ctExpanded
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .background(color = Color.White),
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = ctSelectedOptionText,
                    onValueChange = { },
                    label = { Text("General") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = ctExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = Color.White)
                )
                ExposedDropdownMenu(
                    expanded = ctExpanded,
                    onDismissRequest = {
                        ctExpanded = false
                    }
                ) {
                    pdOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                ctSelectedOptionText = selectionOption
                                ctExpanded = false
                            }
                        ) {
                            Text(text = selectionOption)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            ExposedDropdownMenuBox(
                expanded = pdExpanded,
                onExpandedChange = {
                    pdExpanded = !pdExpanded
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .background(color = Color.White),
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = pdSelectedOptionText,
                    onValueChange = { },
                    label = { Text("Package Dimensions (L x W x H)") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = pdExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = pdExpanded,
                    onDismissRequest = {
                        pdExpanded = false
                    }
                ) {
                    pdOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                pdSelectedOptionText = selectionOption
                                pdExpanded = false
                            }
                        ) {
                            Text(text = selectionOption)
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = length,
                onValueChange = {
                    length = it
                },
                label = { Text("Length") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = width,
                onValueChange = {
                    width = it
                },
                label = { Text("Width") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = height,
                onValueChange = {
                    height = it
                },
                label = { Text("Height") }
            )
        }
    }
}


    fun onLengthChange(password: String) {

    }
@Composable
fun GetTileWidget(hint: String, value: String) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(all = 16.dp)) {
        Text(text = hint, style = MaterialTheme.typography.body2.copy(color = hintLightGray))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = value, style = MaterialTheme.typography.body2)
    }
}

@Composable
fun GetTileWidgetWithIcon(hint: String) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(all = 16.dp)) {
        Text(text = hint, style = MaterialTheme.typography.body2.copy(color = hintLightGray))
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.ic_pdf_icon),
            contentDescription = "pdf",
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp),
        )

    }
}