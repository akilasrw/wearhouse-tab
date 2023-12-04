package com.aeroclubcargo.warehouse.presentation.cargo_assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.theme.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun CheckPackageItemSheet(
    content: @Composable() () -> Unit,
    modalSheetState: ModalBottomSheetState,
    viewModel: CargoAssignmentViewModel,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val isLoading = viewModel.isLoading.collectAsState()
    val uldPalletVMValue = viewModel.uldPalletVMValue.collectAsState()

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
        ),
        modifier = Modifier.padding(top = 10.dp),
        sheetElevation = 8.dp,
        content = content,
        sheetContent = {
            var flightValue = viewModel.packageNameValue.collectAsState()

            if (isLoading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Add Cargo", style = typography.h6)
                        IconButton(onClick = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.getCargoBookingAssignedCargoList()
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
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = flightValue.value,
                            placeholder = {
                                Text(
                                    text = "AWB Number",
                                )
                            },
                            maxLines = 1,
                            onValueChange = {
                                viewModel.setFlightULDValue(it)
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Gray2
                            ),
                            label = { Text("AWB Number") }, // Replace with your label text
                            modifier = Modifier.weight(0.3f),
                            textStyle = TextStyle(color = Color.Black) // Optional: Customize text color
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Button(
                            modifier = Modifier
                                .weight(0.2f)
                                .wrapContentSize(align = Alignment.Center),
                            onClick = {
                                viewModel.setFlightULDValue(flightValue.value)
                                keyboardController?.hide()
                            },
                        ) {
                            Text(text = "Find", style = TextStyle(color = Color.White))
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .weight(0.25f)

                        ) {
                            HeaderTile(
                                title = "Max Weight",
                                desctiption = "${uldPalletVMValue.value?.maxWeight} kg"
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .weight(0.25f)

                        ) {
                            HeaderTile(
                                title = "Received Weight",
                                desctiption = "${uldPalletVMValue.value?.weight} kg",
                                textColor = Green
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .weight(0.25f)

                        ) {
                            HeaderTile(
                                title = "Max Volume",
                                desctiption = "${uldPalletVMValue.value?.maxVolume} m3"
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .weight(0.25f)

                        ) {
                            HeaderTile(
                                title = "Received Volume",
                                desctiption = "${if (uldPalletVMValue.value != null) uldPalletVMValue.value?.volume else 0}  ",
                                textColor = Green
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp)
                    ) {
                        Divider(color = Gray4)
                        FlightsTable(viewModel = viewModel, modalSheetState = modalSheetState)
                        Spacer(modifier = Modifier.height(1.dp))
                    }
                }
        },
        sheetState = modalSheetState
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlightsTable(viewModel: CargoAssignmentViewModel, modalSheetState: ModalBottomSheetState) {
    val coroutineScope = rememberCoroutineScope()
    val showAlert = remember { mutableStateOf(false) }
    var alertMessage = remember {
        mutableStateOf("")
    }
    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            title = { Text("Warning!") },
            text = { Text(alertMessage.value) },
            confirmButton = {
                Button(
                    onClick = { showAlert.value = false }
                ) {
                    Text(stringResource(R.string.ok), style = TextStyle(color = Color.White))
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }


    val mContext = LocalContext.current
    val todoListState = viewModel.listOfPackages.collectAsState()
    val headerStyle =
        MaterialTheme.typography.body2.copy(color = Black, fontWeight = FontWeight.Light)
    val subDataStyle =
        MaterialTheme.typography.body2.copy(color = Black, fontWeight = FontWeight.Bold)
    val uldPalletVMValue = viewModel.uldPalletVMValue.collectAsState()

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
                TableCell(text = "AWB Number", weight = column6Weight, style = headerStyle)
                TableCell(text = "Booking ID", weight = column6Weight, style = headerStyle)
                TableCell(text = "Total Weight", weight = column6Weight, style = headerStyle)
                TableCell(text = "Total Volume", weight = column6Weight, style = headerStyle)
                TableCell(text = "Num of Pieces", weight = column6Weight, style = headerStyle)
                TableCell(text = "Add", weight = column1Weight, style = headerStyle)
            }
        }

        // data
        if (todoListState.value != null) {
            items(todoListState.value!!) { uldModel ->
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
                        TableCell(
                            text = uldModel.awbNumber,
                            weight = column6Weight,
                            style = subDataStyle
                        )
                        TableCell(
                            text = uldModel.bookingNumber,
                            weight = column6Weight,
                            style = subDataStyle
                        )
                        TableCell(
                            text = "${uldModel.totalWeight}",
                            weight = column6Weight,
                            style = subDataStyle
                        )
                        TableCell(
                            text = "${uldModel.totalVolume}",
                            weight = column6Weight,
                            style = subDataStyle
                        )
                        TableCell(
                            text = "${uldModel.numberOfBoxes}",
                            weight = column6Weight,
                            style = subDataStyle
                        )
                        Row(
                            modifier = Modifier
                                .padding(0.dp)
                                .weight(column1Weight),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = {
                                    isExpanded = !isExpanded
                                },
                                enabled = (uldModel.packageItems == null || uldModel.packageItems!!.isEmpty())
                            ) {
                                Icon(
                                    painter = if (isExpanded) painterResource(R.drawable.baseline_keyboard_arrow_up_24) else painterResource(
                                        R.drawable.baseline_keyboard_arrow_down_24
                                    ),
                                    contentDescription = "delete",
                                    tint = BlueLight
                                )
                            }

                        }
                    }
                    if (isExpanded) {
                        Divider(modifier = Modifier.height(2.5.dp), color = Color.White)
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Gray7)
                                    .padding(8.dp)
                            ) {
                                SubTableCell(text = "Package ID", weight = column1Weight)
                                SubTableCell(text = "Dimensions (LxWxH)", weight = column6Weight)
                                SubTableCell(text = "Package Weight", weight = column1Weight)
                                SubTableCell(text = "Package Volume", weight = column1Weight)
                                SubTableCell(
                                    text = "Add Pallet",
                                    weight = column1Weight,
                                    align = TextAlign.Center
                                )

                            }
                            // Sub-table Rows
                            uldModel.packageItems?.forEach { packageItem ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                ) {
                                    SubTableCell(
                                        text = "${packageItem.packageRefNumber}",
                                        weight = column1Weight
                                    )
                                    SubTableCell(
                                        text = "${packageItem.length}x${packageItem.width}x${packageItem.height}",
                                        weight = column6Weight
                                    )
                                    SubTableCell(
                                        text = "${packageItem.weight}kg",
                                        weight = column1Weight
                                    )
                                    SubTableCell(
                                        text = "${packageItem.length * packageItem.width * packageItem.height}",
                                        weight = column1Weight
                                    )
                                    if ((uldPalletVMValue.value != null) && (packageItem.assignedUldId == uldPalletVMValue.value?.id)) {
                                        Row(
                                            modifier = Modifier
                                                .padding(0.dp)
                                                .weight(column1Weight),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    // Do
                                                },
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.ic_accepted),
                                                    contentDescription = "done status",
                                                    tint = Green,
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                        .padding(3.dp),
                                                )
                                            }

                                            IconButton(
                                                onClick = {
                                                    viewModel.removeCargoFromUld(packageItem.id)
                                                    coroutineScope.launch {
                                                        modalSheetState.hide()
                                                        modalSheetState.show()
                                                    }
                                                },
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.outline_remove_check_box_24),
                                                    contentDescription = "delete",
                                                    tint = BlueLight,
                                                    modifier = Modifier
                                                        .size(28.dp)
                                                        .padding(3.dp),
                                                )
                                            }
                                        }
                                    } else if (packageItem.assignedUldId == null || packageItem.assignedUldId!!.isEmpty()) {
                                        Row(
                                            modifier = Modifier
                                                .padding(0.dp)
                                                .weight(column1Weight),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    coroutineScope.launch {
                                                        if (viewModel.validatePackageAvailability(
                                                                packageItem = packageItem,
                                                                messageCallback = { message ->
                                                                    coroutineScope.launch {
                                                                        alertMessage.value = message
                                                                        showAlert.value = true
                                                                    }
                                                                })
                                                        ) {
                                                            viewModel.assignCargoToUld(packageItemId = packageItem.id)
                                                            coroutineScope.launch {
                                                                modalSheetState.hide()
                                                                modalSheetState.show()
                                                            }
                                                        }

                                                    }
                                                },
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.ic_add),
                                                    contentDescription = "Add",
                                                    tint = BlueLight
                                                )
                                            }
                                        }
                                    } else if (packageItem.assignedUldId!!.isNotEmpty() && packageItem.assignedUldId != uldPalletVMValue.value?.id) {
                                        Row(
                                            modifier = Modifier
                                                .padding(0.dp)
                                                .weight(column1Weight),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    coroutineScope.launch {
                                                        alertMessage.value = "This Item has been assigned to different ULD!"
                                                        showAlert.value  = true
                                                    }

                                                },
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.ic_add),
                                                    contentDescription = "disable button",
                                                    tint = Gray1
                                                )
                                            }
                                        }
                                    }

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
    align: TextAlign? = null,
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(4.dp),
        style = typography.subtitle1.copy(fontSize = 13.sp),
        textAlign = align ?: TextAlign.Center
    )
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    style: TextStyle = typography.body2
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
val column6Weight = .2f
val column7Weight = .23f
val column8Weight = .125f
val column9Weight = .125f
val column10Weight = .09f



