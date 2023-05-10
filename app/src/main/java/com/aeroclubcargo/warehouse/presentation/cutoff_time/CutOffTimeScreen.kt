package com.aeroclubcargo.warehouse.presentation.cutoff_time

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.Gray1
import com.aeroclubcargo.warehouse.theme.Gray2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CutOffTimeScreen(navController: NavController, viewModel: CutOffTimeViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = true)
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
                .fillMaxSize()
                .height(80.dp)
                .padding(4.dp)){
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
            }


        }
    }
}


