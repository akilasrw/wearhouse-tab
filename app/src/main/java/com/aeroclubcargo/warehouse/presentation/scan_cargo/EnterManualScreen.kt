package com.aeroclubcargo.warehouse.presentation.scan_cargo

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.presentation.Screen
import com.aeroclubcargo.warehouse.theme.Gray1
import com.aeroclubcargo.warehouse.theme.Gray2
import com.aeroclubcargo.warehouse.theme.Gray3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun EnterManualScreenView(viewModel: ScanCargoViewModel, navController: NavController,){
    val keyboardController = LocalSoftwareKeyboardController.current

    val awbNumber = remember { mutableStateOf("") }

    val awbName = viewModel.awbNameValue.collectAsState()
    val isLoading  = viewModel.isLoading.collectAsState()



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth(fraction = 0.8f)
                .fillMaxHeight(fraction = 0.4f)
                .background(color = Gray3),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                   if(isLoading.value){
                       CircularProgressIndicator()
                   }else{
                       Row(
                           modifier = Modifier
                               .wrapContentSize(align = Alignment.CenterStart)
                       ) {
                           OutlinedTextField(
                               value = awbName.value,
                               onValueChange = {
                                   viewModel.setAWBNumber(it)
                               },
                               placeholder = {
                                   Text(text = "AWB number")
                               },
                               singleLine = true,
                               keyboardOptions = KeyboardOptions(
                                   keyboardType = KeyboardType.Text,
                                   imeAction = ImeAction.Done
                               ),
                               modifier = Modifier
                                   .width(200.dp)
                                   .height(50.dp).background(color = Color.White),
                               colors = TextFieldDefaults.outlinedTextFieldColors(
                                   unfocusedBorderColor = Gray2
                               ),
                           )
                           Spacer(modifier = Modifier.width(10.dp))
                           Button(
                               modifier = Modifier.wrapContentSize(align = Alignment.Center),
                               onClick = {
                                   if(viewModel.awbNameValue.value.isEmpty()){
                                       return@Button
                                   }else{
                                       if(viewModel.awbNameValue.value.isNotEmpty()){
                                           keyboardController?.hide()
                                           navController.navigate(Screen.VerifyBookingScreen.route+"/${viewModel.awbNameValue.value}")
                                       }
                                   }
                               },
                           ) {
                               Text(text = "Find", style = TextStyle(color = Color.White))
                           }
                       }
                   }


            }



        }
    }
}

