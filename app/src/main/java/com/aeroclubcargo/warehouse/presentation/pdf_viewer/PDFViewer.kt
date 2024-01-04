package com.aeroclubcargo.warehouse.presentation.pdf_viewer

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.domain.model.CargoPositionVM
import com.aeroclubcargo.warehouse.domain.model.FlightScheduleModel
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.presentation.uld_position.GetULTMasterUI
import com.aeroclubcargo.warehouse.presentation.uld_position.UldPositionViewModel
import com.aeroclubcargo.warehouse.theme.BlueLight
import com.aeroclubcargo.warehouse.theme.BlueLight3

@Composable
fun RenderHTMLInWebView(
    navController: NavController,
    scheduleModel: FlightScheduleModel?,
    viewModel: UldPositionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var webView: WebView? = null

    LaunchedEffect(key1 = true) {
        scheduleModel?.let {
            viewModel.setFlightSchedule(it)
        }
    }

    val PDFHTMKDetails = viewModel.PDFHTMKDetails.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()



    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                exportAsPdf(webView = webView, context = context)
            },
            modifier = Modifier.padding(16.dp),
            backgroundColor = BlueLight
        ) {
            Icon(
                painter = painterResource(R.drawable.twotone_local_printshop_24),
                contentDescription = "delete",
                tint = BlueLight3
            )
        }
    }) {
        if (isLoading.value) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (PDFHTMKDetails.value == null) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No Details Available!")
            }
        } else {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier
                ) {
                    AndroidView(
                        factory = { context ->
                            WebView(context)
                                .apply {
                                    webViewClient = WebViewClient()
                                    loadDataWithBaseURL(
                                        null,
                                        PDFHTMKDetails.value!!,
                                        "text/html",
                                        "UTF-8",
                                        null
                                    )

                                }
                        },
                    ) {
                        webView = it
                        it.webViewClient = WebViewClient()
                        it.loadDataWithBaseURL(
                            null,
                            PDFHTMKDetails.value!!,
                            "text/html",
                            "UTF-8",
                            null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
//            Button(
//                onClick = {
//                    exportAsPdf(webView = webView, context = context)
//                }) {
//                Text("Export As PDF")
//            }
            }
        }

    }
}

fun exportAsPdf(webView: WebView?, context: Context) {
    if (webView != null) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter =
            webView.createPrintDocumentAdapter("TestPDF")
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .build()
        printManager.print(
            "TestPDF",
            printAdapter,
            printAttributes
        )
    }
}