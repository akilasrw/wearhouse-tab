package com.aeroclubcargo.warehouse.presentation.cargo_assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.domain.model.ULDPalletVM
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.BlueLight3
import com.aeroclubcargo.warehouse.theme.BlueLight5
import com.aeroclubcargo.warehouse.theme.Gray4
import com.aeroclubcargo.warehouse.theme.Gray6
import com.aeroclubcargo.warehouse.theme.Green
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CargoAssignmentScreen(navController: NavController,uldPalletVM: ULDPalletVM? ,viewModel : CargoAssignmentViewModel = hiltViewModel()){

    uldPalletVM?.let { viewModel.setULDPalletVM(it) }

    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        val updatePackageSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        )

        GetCutOffTimeList(viewModel, navController, modalSheetState = updatePackageSheetState)
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun GetCutOffTimeList(
    viewModel: CargoAssignmentViewModel,
    navController: NavController,
    modalSheetState: ModalBottomSheetState,
) {
    val coroutineScope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current
    val mContext = LocalContext.current

    val uldPalletVMValue = viewModel.uldPalletVMValue.collectAsState()
    val isLoading  = viewModel.isLoading.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    ) {
        Text(text = "Cargo Assignment")
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Row(modifier = Modifier
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ULD Number",
                            desctiption = uldPalletVMValue.value?.serialNumber?: "-"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "ULD Type",
                            desctiption =  Constants.getULDType( uldPalletVMValue.value?.uldType)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Dimen (LxWxH)",
                            desctiption = "${uldPalletVMValue.value?.length} x ${uldPalletVMValue.value?.width} x ${uldPalletVMValue.value?.height}"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Max Weight",
                            desctiption =  "${uldPalletVMValue.value?.maxWeight} kg"
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Received Weight",
                            desctiption =  "${uldPalletVMValue.value?.weight} kg", // TODO apply calculated weight
                            textColor = Green
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Max Volume",
                            desctiption = "${uldPalletVMValue.value?.maxVolume } m3"
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        HeaderTile(
                            title = "Received Volume",
                            desctiption = "${(uldPalletVMValue.value!!.length * uldPalletVMValue.value!!.width * uldPalletVMValue.value!!.height )}", // TODO apply calculated volume
                            textColor = Green
                        )
                    }
                }
                if (isLoading.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Spacer(modifier = Modifier.width(6.dp))
                    Divider(color = Gray4)
                    Spacer(modifier = Modifier.width(6.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Added Cargo")
                        Button(onClick = {
                            coroutineScope.launch {
//                                viewModel.refreshAllULDList()
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else {
                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }

                        }) {
                            Text(text = "Add Cargo", style = TextStyle(color = Color.White))
                        }
                    }
//                    FlightsTable(viewModel= viewModel, navController = navController)
                }
            }

        }
    }
}

@Composable
fun HeaderTile(title:String,desctiption:String,textColor: Color =  Color.Black) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(BlueLight3)
        .border(1.dp, BlueLight5, RoundedCornerShape(5.dp))
        .padding(8.dp)
    ) {
        Column() {
            Text(text = title, style = MaterialTheme.typography.subtitle2.copy(color = Gray6, fontSize = 12.sp),)
            Text(text = desctiption,style = MaterialTheme.typography.subtitle1.copy(color = textColor,fontSize = 14.sp))
        }
    }
}