package com.aeroclubcargo.warehouse.presentation.scan_cargo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Paint
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
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.theme.Gray3
import com.aeroclubcargo.warehouse.utils.QrCodeAnalyzer

@Composable
fun BarCodeScanView(  viewModel: ScanCargoViewModel){
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp)) {

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White), contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(600.dp)
                    .fillMaxWidth()
            ) {
                if (hasCameraPermission) {
                    Image(painter = painterResource(id = R.drawable.ic_qr_code),
                        modifier = Modifier.fillMaxWidth()
                        , contentDescription = " QR Icon" )

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
                    Column() {
                        Image(painter = painterResource(id = R.drawable.ic_qr_code),
                            modifier = Modifier.fillMaxWidth()
                            , contentDescription = " QR Icon" )
                        Text(text = "Please Enable Permission")
                    }
                }
            }

        }
    }
}