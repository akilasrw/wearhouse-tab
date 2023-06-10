package com.aeroclubcargo.warehouse.presentation.verify_booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Devices.NEXUS_5
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aeroclubcargo.warehouse.theme.Gray4
import kotlinx.coroutines.launch


@Preview(device = Devices.NEXUS_10)
@ExperimentalMaterialApi
@Composable
fun UpdatePackageBottomSheet(
//    content: @Composable() () -> Unit,
//    modalSheetState: ModalBottomSheetState,
//    viewModel: VerifyBookingViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val contextForToast = LocalContext.current.applicationContext
    var selectedOption by remember { mutableStateOf("Option 1") }
    val options = listOf("Option 1", "Option 2", "Option 3")
    var expanded by remember {
        mutableStateOf(false)
    }

//    ModalBottomSheetLayout(
//        sheetShape = RoundedCornerShape(
//            topStart = 20.dp,
//            topEnd = 20.dp,
//        ),
//        sheetElevation = 8.dp,
//        content = content,
//        sheetContent = {


    Column(
        modifier = Modifier
            .fillMaxSize().background(color = Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Update Booking", style = MaterialTheme.typography.h6)
            IconButton(onClick = {
                coroutineScope.launch {
//                            modalSheetState.hide()
                }
            }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "back button",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
        Divider(color = Gray4)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.padding(16.dp).fillMaxWidth(fraction = 0.2f)) {
                Text("Select an option:")
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Gray4,
                        shape = MaterialTheme.shapes.medium
                    )) {
                    TextButton(
                        onClick = { expanded = true }
                    ) {
                        Text(selectedOption)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOption = option
                                    expanded = false
                                }
                            ) {
                                Text(option)
                            }
                        }
                    }
                }
            }
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = {
//                            expanded = false
//                        }
//                    ) {
//                        // adding items
////                        listItems.forEachIndexed { itemIndex, itemValue ->
//                            DropdownMenuItem(
//                                onClick = {
////                                    Toast.makeText(contextForToast, itemValue, Toast.LENGTH_SHORT)
////                                        .show()
//                                    expanded = false
//                                },
//                                enabled = true
//                            ) {
//                                Text(text = "Test")
//                            }
////                        }
//                    }
        }
    }
//        },
//        sheetState = modalSheetState
//    )
}
