package com.aeroclubcargo.warehouse.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.theme.BlueLight
import com.aeroclubcargo.warehouse.presentation.theme.Gray1


@Composable
fun LoginScreen(
    navController: NavController?,
) {
    Scaffold() {
        MainUI()
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p)
@Composable
fun MainUI() {


    val image = painterResource(id = R.drawable.ic_login_img)

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val scrollState = rememberScrollState()
    var checkState = remember {
        mutableStateOf(true)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        horizontalAlignment = Alignment.End
    ) {
        Text(text = "English", modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(state = scrollState, orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(70.dp)
                    .width(140.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(painter = image, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(19.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.hello),
                    style = MaterialTheme.typography.h4,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.enter_credentials_msg),
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text(text = "Email") },
                        placeholder = { Text(text = "Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
//                                Icon(
//                                    imageVector = vectorResource(id = R.drawable.password_eye),
//                                    tint = if (passwordVisibility.value) primaryColor else Color.Gray
//                                )
                            }
                        },
                        label = { Text("Password") },
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester = focusRequester),
                    )
                    Spacer(modifier = Modifier.padding(25.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(checked = checkState.value, onCheckedChange = {
                                checkState.value = it
                            })
                            Text(
                                "Remember me",
                                maxLines = 1,
                                style = MaterialTheme.typography.body2.copy(color = Gray1)
                            )
                        }
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = {},
                                border = BorderStroke(1.dp, BlueLight),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.sign_in),
                                    fontSize = 20.sp,
                                    color = BlueLight
                                )
                            }
                        }


                    }


                }
            }
        }
    }
}