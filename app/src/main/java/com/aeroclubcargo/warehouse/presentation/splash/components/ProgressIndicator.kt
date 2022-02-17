package com.aeroclubcargo.warehouse.presentation.splash.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.aeroclubcargo.warehouse.R

@Composable
fun ProgressIndicator(){
    CircularProgressIndicator(
        modifier = Modifier.size(60.dp),
        color = colorResource(id = R.color.teal_700),
       )
}