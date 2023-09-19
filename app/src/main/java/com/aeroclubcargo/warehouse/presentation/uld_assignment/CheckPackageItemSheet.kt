package com.aeroclubcargo.warehouse.presentation.uld_assignment

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.CommonDropDown
import com.aeroclubcargo.warehouse.presentation.components.CommonTextField
import com.aeroclubcargo.warehouse.presentation.components.DropDownModel
import com.aeroclubcargo.warehouse.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//@Preview(device = Devices.NEXUS_10)
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun CheckPackageItemSheet(
    content: @Composable() () -> Unit,
    modalSheetState: ModalBottomSheetState,
    viewModel: ULDAssignmentViewModel,
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
            var flightValue = viewModel.flightULDValue.collectAsState()

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
                    Text(text = "Add ULD", style = typography.h6)
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
                                text = "Search by ULD Number",
                            )
                        },
                        maxLines = 1,
                        onValueChange = {
                            viewModel.setFlightULDValue(it)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Gray2
                        ),
                        label = { Text("Search by ULD Number") }, // Replace with your label text
                        modifier = Modifier.weight(0.4f),
                        textStyle = TextStyle(color = Color.Black) // Optional: Customize text color
                    )
                    Button(
                        modifier = Modifier
                            .weight(0.2f)
                            .wrapContentSize(align = Alignment.Center),
                        onClick = {
                            keyboardController?.hide()
                        },
                    ) {
                        Text(text = "Find", style = TextStyle(color = Color.White))
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                }
                Column(modifier = Modifier.fillMaxHeight()) {
                    Divider(color = Gray4)
                    ULDRowItemTitle()
                    ULDTable()
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }
        },
        sheetState = modalSheetState
    )
}

@Composable
fun ULDTable() {
    // Sample data for the table
    val tableData = listOf(
        ULDInfo("ULD001", "Type A", "120x80x60 cm", "100 kg", "20 kg",false),
        ULDInfo("ULD002", "Type B", "90x60x50 cm", "80 kg", "20 kg",true),
        ULDInfo("ULD003", "Type A", "150x100x70 cm", "120 kg", "20 kg",false),
        ULDInfo("ULD004", "Type C", "80x60x40 cm", "50 kg", "20 kg",false),
        ULDInfo("ULD005", "Type A", "110x70x50 cm", "90 kg", "20 kg",true),
        ULDInfo("ULD005", "Type A", "110x70x50 cm", "90 kg", "20 kg",true),
    )

    var selectedRows by remember { mutableStateOf(setOf<ULDInfo>()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(tableData) { rowData ->
            ULDRowItem(
                uldInfo = rowData,
                isChecked = selectedRows.contains(rowData),
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        selectedRows = selectedRows + rowData
                    } else {
                        selectedRows = selectedRows - rowData
                    }
                }
            )
        }
    }
}

data class ULDInfo(
    val uldNumber: String,
    val type: String,
    val dimensions: String,
    val maxWeight: String,
    var maxVolume: String,
    val isSelected: Boolean
)

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


@Composable
fun ULDRowItemTitle(
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Gray5)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(modifier = Modifier.weight(column1Weight))
        Text(text = "ULD Number")
        Spacer(modifier = Modifier.weight(column2Weight))
        Text(text = "ULD type")
        Spacer(modifier = Modifier.weight(column3Weight))
        Text(text = "Dimensions (L x W x H) cm")
        Spacer(modifier = Modifier.weight(column4Weight))
        Text(text = "Max Weight")
        Spacer(modifier = Modifier.weight(column5Weight))
        Text(text = "Max Volume") // Replace with your data for Column E
        Spacer(modifier = Modifier.weight(column6Weight))
        Text(text = "Add") //
    }
}

@Composable
fun ULDRowItem(
    uldInfo: ULDInfo,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(modifier = Modifier.weight(column1Weight))
        Text(text = uldInfo.uldNumber)
        Spacer(modifier = Modifier.weight(column2Weight))
        Text(text = uldInfo.type)
        Spacer(modifier = Modifier.weight(column3Weight))
        Text(text = uldInfo.dimensions)
        Spacer(modifier = Modifier.weight(column4Weight))
        Text(text = uldInfo.maxWeight)
        Spacer(modifier = Modifier.weight(column5Weight))
        Text(text = uldInfo.maxVolume) // Replace with your data for Column E
        Spacer(modifier = Modifier.weight(column6Weight))
        Checkbox(
            checked = isChecked,
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

