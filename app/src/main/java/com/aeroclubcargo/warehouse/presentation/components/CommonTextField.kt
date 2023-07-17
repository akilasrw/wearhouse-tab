package com.aeroclubcargo.warehouse.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aeroclubcargo.warehouse.theme.Gray2

@Composable
fun CommonTextField(
    label: String,
    value:String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    padding: Dp = 16.dp
){
    Column(Modifier.padding(padding).fillMaxWidth()) {
        Text(text = label,style = MaterialTheme
            .typography
            .subtitle2)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = label, style = MaterialTheme
                    .typography
                    .subtitle2)
            },
            singleLine = true,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .background(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Gray2
            ),
        )

    }
}