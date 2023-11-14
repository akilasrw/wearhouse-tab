package com.aeroclubcargo.warehouse.presentation.cargo_assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.common.Constants.getULDType
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.theme.*
import kotlinx.coroutines.launch


val column1Weight = .1f
val column2Weight = .12f
val column3Weight = .12f
val column4Weight = .1f
val column5Weight = .1f
val column6Weight = .1f
val column8Weight = .125f
val column9Weight = .12f
val column10Weight = .09f

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
                            .weight(0.2f)
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
                        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                            Icon(painter = painterResource(id = R.drawable.baseline_save_24), contentDescription = "update")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Update", style = TextStyle(color = Color.White))
                        }
                    }
                }

                Column(modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)) {
                    Divider(color = Gray4)
                    ULDRowItemTitle()
                    CargoPackageItemTable(viewModel = viewModel)
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }
        },
        sheetState = modalSheetState
    )
}

@Composable
fun CargoPackageItemTable(viewModel: CargoAssignmentViewModel) {
    var expandedRows by remember { mutableStateOf(emptySet<Int>()) }
    val valueList = viewModel.values.collectAsState()
    LazyColumn{
        items(viewModel.values.value!!.size) { index ->
            val rowData = valueList.value?.get(index)
            if(rowData == null){
                Box {

                }
            }else{
                TableRow(
                    rowData = rowData,
                    isExpanded = index in expandedRows,
                    onRowClicked = {
                        expandedRows = if (index in expandedRows) {
                            expandedRows - index
                        } else {
                            expandedRows + index
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun ULDRowItemTitle(
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Gray5)
            .padding(8.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TableCellText(text = "AWB Number", weight = (column2Weight),)
        TableCellText(text = "Booking ID", weight = (column1Weight),)
        TableCellText(text = "Total Weight",weight = (column8Weight) ,)
        TableCellText(text = "Total Volume",weight = (column4Weight) ,)
        TableCellText(text = "Num of Pieces",weight = (column5Weight) )
        TableCellText(text = "Add",weight = (column6Weight))
    }
}

@Composable
fun TableRow(rowData: Object, isExpanded: Boolean, onRowClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRowClicked() }
            .background(if (isExpanded) Color.Gray else Color.Transparent)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
//            TableCellText(text = rowData.awbNumber, weight = 2f)
//            TableCellText(text = rowData.bookingId, weight = 1f)
//            TableCellText(text = rowData.totalWeight, weight = 1f)
//            TableCellText(text = rowData.totalVolume, weight = 1f)
//            TableCellText(text = rowData.numOfPieces, weight = 1f)
            TableCellText(text = "rowData.awbNumber", weight = column2Weight)
            TableCellText(text = "rowData.bookingId", weight = column1Weight)
            TableCellText(text = "rowData.totalWeight", weight = column8Weight)
            TableCellText(text = "rowData.totalVolume", weight = column4Weight)
            Box (modifier = Modifier
                .weight(column5Weight)
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(8.dp)) {
                IconButton(onClick = {
                    onRowClicked()
                }) {
                    Icon(painter = painterResource(id = if (isExpanded) { R.drawable.baseline_keyboard_arrow_down_24 } else {R.drawable.baseline_keyboard_arrow_up_24}),
                        contentDescription = "dropdown",
                        tint = BlueLight2
                        )
                }
            }
        }

        // Additional content for expanded row
        if (isExpanded) {
            Row {
                TableCellText(text = "rowData.awbNumber", weight = column2Weight)
                TableCellText(text = "rowData.bookingId", weight = column1Weight)
                TableCellText(text = "rowData.totalWeight", weight = column8Weight)
                TableCellText(text = "rowData.totalVolume", weight = column4Weight)
                TableCellText(text = "rowData.numOfPieces", weight = column5Weight)
            }
        }
    }
}

@Composable
fun ULDRowItem(
    uldInfo: ULDPalletVM,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TableCellText(text = uldInfo.serialNumber,weight = column2Weight)
        TableCellText(text = getULDType(uldInfo.uldType), weight = column1Weight)
        TableCellText(text = "${uldInfo.length} x ${uldInfo.width} x ${uldInfo.height}",weight = column8Weight)
        TableCellText(text = "${uldInfo.maxWeight}",weight = column4Weight )
        TableCellText(text = "${uldInfo.maxVolume}",weight = column5Weight)
        Checkbox(
            checked = isChecked,
            modifier = Modifier.weight(column6Weight),
            colors = CheckboxDefaults.colors(
                checkedColor = Green,
                uncheckedColor = BlueLight2,
            ),
            onCheckedChange = { newCheckedState ->
                onCheckedChange(newCheckedState)
            }
        )
    }
}

@Composable
fun RowScope.TableCellText(
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
        textAlign = TextAlign.Center
    )
}



