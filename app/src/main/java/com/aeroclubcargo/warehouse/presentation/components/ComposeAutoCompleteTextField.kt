//package com.aeroclubcargo.warehouse.presentation.components
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
////import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.TextFieldValue
////import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.delay
//
//@Composable
//fun AutoCompleteTextField(
//    initialText: String,
//    itemList: List<String>,
//    onQuery: (String) -> Unit,
//    onClearResults: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val (field, changeField) = savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue(text = initialText) }
//    LaunchedEffect(block = field.text) {
//        if (!field.isBlank()) {
//            delay(1000L)
//            onQuery(field.text.trim())
//        }
//    }
//    LazyColumn(modifier = modifier.animateContentSize()) {
//        item {
//            TextField(
//                value = field,
//                onValueChange = {
////                    changeField(it)
//                    onClearResults()
//                },
//                maxLines = 1
//            )
//        }
//        if (itemList.isNotEmpty() && !field.isBlank()) {
//            items(itemList) { item ->
//                Text(
//                    item,
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .clickable(onClick = {
//                            changeField(TextFieldValue(item))
//                        })
//                )
//            }
//        }
//    }
//}
//
//fun TextFieldValue.isBlank() = this.text.isBlank()