package com.aeroclubcargo.warehouse

import android.app.Application
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class WarehouseApplication : Application(){

    var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        Stetho.initializeWithDefaults(this)

    }

}