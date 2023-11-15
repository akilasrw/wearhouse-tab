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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.Constants.getULDType
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.presentation.recieved_cargo_details.ReceivedCargoDetailVM
import com.aeroclubcargo.warehouse.theme.*
import com.aeroclubcargo.warehouse.utils.toDateTimeDisplayFormat
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
                            modifier = Modifier.weight(0.4f),
                            textStyle = TextStyle(color = Color.Black) // Optional: Customize text color
                        )
                        Button(
                            modifier = Modifier
                                .weight(0.1f)
                                .wrapContentSize(align = Alignment.Center),
                            onClick = {
                                viewModel.setFlightULDValue(flightValue.value)
                                keyboardController?.hide()
                            },
                        ) {
                            Text(text = "Find", style = TextStyle(color = Color.White))
                        }
                        Button(
                            modifier = Modifier
                                .weight(0.2f)
                                .wrapContentSize(align = Alignment.Center),
                            onClick = {
                                // TODO
//                            viewModel.updateData(onComplete = {
//                                coroutineScope.launch {
//                                    modalSheetState.hide()
//                                }
//                            })
                                keyboardController?.hide()
                            },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_save_24),
                                    contentDescription = "update"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Update", style = TextStyle(color = Color.White))
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp)
                    ) {
                        Divider(color = Gray4)
                        FlightsTable(viewModel = viewModel)
                        Spacer(modifier = Modifier.height(1.dp))
                    }
                }
        },
        sheetState = modalSheetState
    )
}


@Composable
fun FlightsTable(viewModel: CargoAssignmentViewModel) {
    val mContext = LocalContext.current
    val todoListState = viewModel.listOfPackages.collectAsState()
    val headerStyle = MaterialTheme.typography.body2.copy(color = Black, fontWeight = FontWeight.Light)
    val subDataStyle = MaterialTheme.typography.body2.copy(color = Black, fontWeight = FontWeight.Bold)

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
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Gray7)
                                    .padding(8.dp)
                            ) {
                                SubTableCell(text = "Package ID", weight = column1Weight)
                                SubTableCell(text = "Dimensions (LxWxH)", weight = column7Weight)
                                SubTableCell(text = "Package Weight", weight = column1Weight)
                                SubTableCell(text = "Add Pallet", weight = column1Weight)

                            }
                            // Sub-table Rows
                            uldModel.packageItems?.forEach { packageItem ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White)
                                        .padding(8.dp)
                                ) {
                                    SubTableCell(text = "${packageItem.packageRefNumber}", weight = column1Weight)
                                    SubTableCell(text = "${packageItem.length}x${packageItem.width}x${packageItem.height}", weight = column7Weight)
                                    SubTableCell(text = "${packageItem.weight}kg", weight = column1Weight)
                                    Row(
                                        modifier = Modifier
                                            .padding(0.dp)
                                            .weight(column1Weight),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        IconButton(
                                            onClick = {

                                            },
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_add),
                                                contentDescription = "delete",
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
        style = typography.subtitle1.copy(fontSize = 13.sp),
        textAlign = TextAlign.Start
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
val column7Weight = .24f
val column8Weight = .125f
val column9Weight = .125f
val column10Weight = .09f



