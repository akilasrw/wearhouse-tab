package com.aeroclubcargo.warehouse

import android.app.Application
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WarehouseApplication : Application(){

    var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}