package com.aeroclubcargo.warehouse.presentation.verify_booking

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.CommonDropDown
import com.aeroclubcargo.warehouse.presentation.components.CommonTextField
import com.aeroclubcargo.warehouse.presentation.components.DropDownModel
import com.aeroclubcargo.warehouse.theme.BlueLight2
import com.aeroclubcargo.warehouse.theme.Gray4
import com.aeroclubcargo.warehouse.theme.hintLightGray
import kotlinx.coroutines.launch


//@Preview(device = Devices.NEXUS_10)
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun UpdatePackageBottomSheet(
    content: @Composable() () -> Unit,
    modalSheetState: ModalBottomSheetState,
    viewModel: VerifyBookingViewModel,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val height = viewModel.heightValue.collectAsState()
    val width = viewModel.widthValue.collectAsState()
    val length = viewModel.lengthValue.collectAsState()
    val weight =   viewModel.weightValue.collectAsState()
    val noOfPackagesValue =  viewModel.noOfPackagesValue.collectAsState()

    val packageItemCategory =   viewModel.packageItemCategory.collectAsState()
    val selectedVolumeUnit =   viewModel.selectedVolumeUnit.collectAsState()
    val selectedWeightUnit =   viewModel.selectedWeightUnit.collectAsState()

    val loginState = viewModel.isLoading.observeAsState().value ?: false

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
        ),
        sheetElevation = 8.dp,
        content = content,
        sheetContent = {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        if(loginState){
           Spacer(modifier = Modifier.fillMaxWidth())
        } else
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
        Column(modifier = Modifier.fillMaxHeight(0.8f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier
                    .weight(1f)) {
                    CommonDropDown(
                        label = "Cargo Type",
                        items = viewModel.getCargoPackageItemCategories() ,
                        selectedIndex = packageItemCategory
                    ) {
                        viewModel.setPackageItemCategory(it.ordinal)
                    }
                }
                Spacer(modifier = Modifier.width(1.dp))
                Row(modifier = Modifier
                    .weight(1f)) {
                    CommonTextField(
                        label = "Length",
                        value = length.value.toString(),
                        keyboardOptions =  KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = {
                            if(it.isEmpty()){
                                viewModel.setLength(0.0)
                            }else{
                                viewModel.setLength(it.toDouble())
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.width(1.dp))
                Row(modifier = Modifier
                    .weight(1f)) {
                    CommonTextField(
                        label = "Width",
                        value = width.value.toString(),
                        keyboardOptions =  KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = {
                            if(it.isEmpty()){
                                viewModel.setWidth(0.0)
                            }else{
                                viewModel.setWidth(it.toDouble())
                            }

                        }
                    )
                }
                Spacer(modifier = Modifier.width(1.dp))
                Row(modifier = Modifier
                    .weight(1f)) {
                    CommonTextField(
                        label = "Height",
                        value = height.value.toString(),
                        keyboardOptions =  KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = {
                            if(it.isEmpty()){
                                viewModel.setHeight(0.0)
                            }else{
                                viewModel.setHeight(it.toDouble())
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.width(1.dp))
                Row(modifier = Modifier
                    .weight(1f)) {
                    CommonDropDown(
                        label = "Unit",
                        items = viewModel.getLengthUnitList().map { DropDownModel( it ,it.name) }
                            .toList(),
                        selectedIndex = selectedVolumeUnit
                    ) {
                        viewModel.setVolumeUnit(viewModel.getLengthUnitList().indexOfFirst { item -> item.id == it.id })
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Row(modifier = Modifier
                    .weight(1f)) {
                    CommonTextField(
                        label = "Weight",
                        value = weight.value.toString(),
                        keyboardOptions =  KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = {
                            if(it.isEmpty()){
                                viewModel.setWeight(0.0)
                            }else{
                                viewModel.setWeight(it.toDouble())
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.width(1.dp))
                Row(modifier = Modifier
                    .weight(1f)) {
                    CommonDropDown(
                        label = "Unit",
                        selectedIndex = selectedWeightUnit,
                        items = viewModel.getWeightUnitList().map { DropDownModel( it ,it.name) }
                            .toList()
                    ) {
                        viewModel.setWeightUnit(viewModel.getWeightUnitList().indexOfFirst { item -> item.id == it.id })
                    }
                }
                Spacer(modifier = Modifier.width(1.dp))
                Row(modifier = Modifier
                    .weight(1f)) {
                    CommonTextField(
                        label = "No. of Packages",
                        value = noOfPackagesValue.value.toString(),
                        keyboardOptions =  KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = {
                            if(it.isEmpty()){
                                viewModel.setNoOfPakcages(0.0)
                            }else{
                                viewModel.setNoOfPakcages(it.toDouble())
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.5f))


            }
            Spacer(modifier = Modifier.height(1.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
                horizontalArrangement = Arrangement.End
                ) {
                Button(onClick = {
                    coroutineScope.launch {
                        modalSheetState.hide()
                    }
                }, colors = ButtonDefaults.buttonColors(backgroundColor = hintLightGray)) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                              viewModel.updatePackageItem(onComplete = {
                                  coroutineScope.launch {
                                      modalSheetState.hide()
                                      Toast.makeText(context, "Item Updated Successfully!", Toast.LENGTH_SHORT).show()
//                                      showSnackbarWithDelay(snackbarHostState, "Toast message", 3000)
                                  }
                              })
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BlueLight2)
                ) {
                    Text(
                        text = "Update",
                        style = MaterialTheme.typography.button.copy(color = Color.White)
                    )
                }
            }
        }
    }
        },
        sheetState = modalSheetState
    )
}
