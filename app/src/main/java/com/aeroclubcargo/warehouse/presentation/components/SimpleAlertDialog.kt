package com.aeroclubcargo.warehouse.presentation.components

import android.icu.text.CaseMap
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aeroclubcargo.warehouse.R


@Composable
fun SimpleAlertDialog (show: Boolean,
                       title: String,
                          message : String,
                          onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {

            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = stringResource(R.string.ok), style = MaterialTheme.typography.button) }
            },
            title = { Text(text = title) },
            text = { Text(text = message) }
        )
    }
}