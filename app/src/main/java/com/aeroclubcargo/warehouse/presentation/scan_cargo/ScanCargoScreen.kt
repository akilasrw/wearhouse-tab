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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.BlueLight2
import com.aeroclubcargo.warehouse.theme.Gray3
import com.aeroclubcargo.warehouse.utils.QrCodeAnalyzer


@Preview
@Composable
fun ScanCargoScreen(navController: NavController, viewModel: ScanCargoViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screen.VerifyBookingScreen.route)
        }, backgroundColor = BlueLight2) {
            Icon(
                Icons.Filled.Done,
                contentDescription = "done",
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.White
            )
        }
    }) {
        var code by remember {
            mutableStateOf("")
        }

        val lifeCycleOwner = LocalLifecycleOwner.current
        val context = LocalContext.current
        val cameraProvider = remember {
            ProcessCameraProvider.getInstance(context)
        }

        var hasCameraPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
        var launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                hasCameraPermission = granted
            }
        )

        LaunchedEffect(key1 = true) {
            launcher.launch(Manifest.permission.CAMERA)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White), contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .width(600.dp)
                    .height(260.dp)
                    .border(width = 30.dp, color = Gray3)
            ) {
                if (hasCameraPermission) {
                    AndroidView(factory = { context ->
                        val previewView = PreviewView(context)
                        val preview = androidx.camera.core.Preview.Builder().build()
                        val selector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                        val imageAnalysis = ImageAnalysis.Builder()
                            .setTargetResolution(
                                Size(previewView.width, previewView.height)
                            ).setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                        imageAnalysis.setAnalyzer(
                            ContextCompat.getMainExecutor(context),
                            QrCodeAnalyzer { result ->
                                code = result
                            }
                        )
                        try {
                            cameraProvider.get()
                                .bindToLifecycle(
                                    lifeCycleOwner,
                                    selector,
                                    preview,
                                    imageAnalysis
                                )
                        } catch (e: Exception) {

                        }
                        previewView
                    })
                    Text(text = code, fontSize = 20.sp)
                } else {
                    Text(text = "Please Enable Permission")
                }
            }

        }
    }
}
