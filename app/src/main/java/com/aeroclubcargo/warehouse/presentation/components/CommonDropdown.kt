package com.aeroclubcargo.warehouse.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aeroclubcargo.warehouse.theme.Gray4

data class DropDownModel<T>(var item: T,var label:String){
    override fun toString(): String {
        return label
    }
}

@Composable
fun <T> CommonDropDown(
    label: String,
    items: List<T>,
    onItemSelected: (T) -> Unit
){
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedOption by remember { mutableStateOf(items.first()) }

    Column(Modifier.padding(16.dp).fillMaxWidth()) {
        Text(label, style = MaterialTheme
            .typography
            .subtitle2)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier
            .border(
                width = 1.dp,
                color = Gray4,
                shape = MaterialTheme.shapes.medium
            ).fillMaxWidth()) {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { expanded = true }
            ) {
                Text(selectedOption.toString(), style = MaterialTheme
                    .typography
                    .subtitle2)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = option
                            expanded = false
                            onItemSelected(option)
                        }
                    ) {
                        Text(option.toString(), style = MaterialTheme
                            .typography
                            .subtitle2)
                    }
                }
            }
        }
    }
}