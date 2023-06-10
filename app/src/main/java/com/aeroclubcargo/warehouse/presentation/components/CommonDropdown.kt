package com.aeroclubcargo.warehouse.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aeroclubcargo.warehouse.theme.Gray4

@Composable
fun CommonDropDown(
    
){
    val options = listOf("Option 1", "Option 2", "Option 3")
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedOption by remember { mutableStateOf("Option 1") }

    Column(Modifier.padding(16.dp).fillMaxWidth(fraction = 0.2f)) {
        Text("Select an option:")
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth()
            .border(
                width = 1.dp,
                color = Gray4,
                shape = MaterialTheme.shapes.medium
            )) {
            TextButton(
                onClick = { expanded = true }
            ) {
                Text(selectedOption)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    ) {
                        Text(option)
                    }
                }
            }
        }
    }
}