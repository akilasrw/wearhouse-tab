//import android.os.Bundle
//import android.widget.DatePicker
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import java.text.SimpleDateFormat
//import java.util.*
//
//@ExperimentalComposeUiApi
//@Composable
//fun DateRangePicker(
//    onDateRangeSelected: (DateRange) -> Unit
//) {
//    var startDate by remember { mutableStateOf<Date?>(null) }
//    var endDate by remember { mutableStateOf<Date?>(null) }
//    var isStartDatePickerShown by remember { mutableStateOf(false) }
//    var isEndDatePickerShown by remember { mutableStateOf(false) }
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    Column(
//        modifier = Modifier.padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            BasicTextField(
//                value = startDate?.formatToString() ?: "",
//                onValueChange = {},
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        keyboardController?.hide()
//                        isStartDatePickerShown = true
//                    }
//                ),
//                textStyle = TextStyle(fontSize = 16.sp)
//            )
//            BasicTextField(
//                value = endDate?.formatToString() ?: "",
//                onValueChange = {},
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        keyboardController?.hide()
//                        isEndDatePickerShown = true
//                    }
//                ),
//                textStyle = TextStyle(fontSize = 16.sp)
//            )
//        }
//
//        Button(
//            onClick = {
//                onDateRangeSelected(DateRange(startDate, endDate))
//            },
//            enabled = startDate != null && endDate != null
//        ) {
//            Text(text = "Apply")
//        }
//
//        if (isStartDatePickerShown) {
//            DatePicker1(
//                selectedDate = startDate,
//                onDateSelected = { date ->
//                    startDate = date
//                    isStartDatePickerShown = false
//                }
//            )
//        }
//
//        if (isEndDatePickerShown) {
//            DatePicker1(
//                selectedDate = endDate,
//                onDateSelected = { date ->
//                    endDate = date
//                    isEndDatePickerShown = false
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun DatePicker1(
//    selectedDate: Date?,
//    onDateSelected: (Date) -> Unit
//) {
//    var selectedDate by remember { mutableStateOf(selectedDate ?: Date()) }
//
////    DatePicker(
//////        date = selectedDate,
//////        onDateChange = {
//////            selectedDate = it
//////            onDateSelected(it)
//////        }
////    )
//}
//
//fun Date.formatToString(): String {
//    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//    return formatter.format(this)
//}
//
//data class DateRange(
//    val startDate: Date?,
//    val endDate: Date?
//)