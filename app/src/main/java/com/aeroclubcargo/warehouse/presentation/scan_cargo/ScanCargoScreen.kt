@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.aeroclubcargo.warehouse.presentation.scan_cargo

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.BlueLight2
import com.aeroclubcargo.warehouse.theme.Gray3
import com.aeroclubcargo.warehouse.theme.Gray4
import com.aeroclubcargo.warehouse.utils.QrCodeAnalyzer


@Preview
@Composable
fun ScanCargoScreen(navController: NavController, viewModel: ScanCargoViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }, floatingActionButton = {
//        FloatingActionButton(onClick = {
//            navController.navigate(Screen.VerifyBookingScreen.route)
//        }, backgroundColor = BlueLight2) {
//            Icon(
//                Icons.Filled.Done,
//                contentDescription = "done",
//                modifier = Modifier.size(ButtonDefaults.IconSize),
//                tint = Color.White
//            )
//        }
    }) {

        var tabIndex by remember { mutableStateOf(0) }

        val tabs = listOf("Scan QR code", "Enter AWB number")


        Column(modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)) {
            Text(text = "Verify Booking")
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White), contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize().padding(top = 20.dp)) {
                    TabRow(selectedTabIndex = tabIndex,
                        backgroundColor = Color(0xff1E76DA),
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .fillMaxWidth(fraction = 0.7f)
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(start = 0.5.dp, end = 0.5.dp)
                            .clip(RoundedCornerShape(50)),
                        indicator = { tabPositions: List<TabPosition> ->
                            Box {}
                        }
                    ) {
                        tabs.forEachIndexed { index, text ->
                            val selected = tabIndex == index
                            Tab(
                                modifier = if (selected) Modifier
                                    .clip(RoundedCornerShape(90))
                                    .padding(0.5.dp)
                                    .background(
                                        Color.White
                                    )
                                else Modifier
                                    .clip(RoundedCornerShape(90))
                                    .padding(0.5.dp)
                                    .background(
                                        Color(
                                            0xff1E76DA
                                        )
                                    ),
                                selected = selected,
                                onClick = { tabIndex = index },
                                text = { Text(text = text, color = Color(0xff6FAAEE)) }
                            )
                        }
                    }
                    Divider(
                        color = Gray4,
                        thickness = 2.dp,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 20.dp,
                                start = 8.dp,
                                end = 8.dp)
                    )
                    when (tabIndex) {
                        0 -> BarCodeScanView(viewModel= viewModel)
                        1 -> EnterManualScreenView(viewModel= viewModel,navController= navController)
                    }
                }
            }
            }

    }

}



