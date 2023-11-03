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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.common.Constants.getULDType
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
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
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)) {
                    Divider(color = Gray4)
                    ULDRowItemTitle()
                    ULDTable(viewModel = viewModel)
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }
        },
        sheetState = modalSheetState
    )
}

@Composable
fun ULDTable(viewModel: ULDAssignmentViewModel) {
    val allListFlow = viewModel.allUldListFlow.collectAsState()
    var selectedRows by remember { mutableStateOf(setOf<ULDPalletVM>()) }
    
    if(allListFlow.value == null){
        Text(text = "No data available!")
    }else  {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(allListFlow.value!!) { uld ->
                ULDRowItem(
                    uldInfo = uld,
                    isChecked = uld.isAssigned,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedRows = selectedRows + uld
                        } else {
                            selectedRows = selectedRows - uld
                        }
                    }
                )
            }
        } 
    }
    
   
}

val column1Weight = .1f
val column2Weight = .12f
val column3Weight = .12f
val column4Weight = .1f
val column5Weight = .1f
val column6Weight = .1f
val column7Weight = .1f
val column8Weight = .125f
val column9Weight = .12f
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
        Text(text = "ULD Number", textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.weight(column2Weight))
        Text(text = "ULD type", textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.weight(column3Weight))
        Text(text = "Dimensions (L x W x H) cm", textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.weight(column4Weight))
        Text(text = "Max Weight", textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.weight(column5Weight))
        Text(text = "Max Volume", textAlign = TextAlign.Start) // Replace with your data for Column E
        Spacer(modifier = Modifier.weight(column6Weight))
        Text(text = "Add", textAlign = TextAlign.Start) //
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
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(modifier = Modifier.weight(column1Weight))
        Text(text = uldInfo.serialNumber, textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.weight(column2Weight))
        Text(text = getULDType(uldInfo.uldType), textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.weight(column3Weight))
        Text(text = "${uldInfo.length} x ${uldInfo.width} x ${uldInfo.height}", textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.weight(column4Weight))
        Text(text = "${uldInfo.maxWeight}", textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.weight(column5Weight))
        Text(text = "${uldInfo.maxVolume}", textAlign = TextAlign.Start) // Replace with your data for Column E
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

