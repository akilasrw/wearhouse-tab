package com.aeroclubcargo.warehouse.presentation.components.top_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.theme.BlueLight

@Composable
fun GetTopBar(
    isDashBoard: Boolean = false,
    navController: NavController,
    viewModel: TopBarViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var showSignOutDialog by remember { mutableStateOf(false) }
    val userModel = viewModel.userValue.collectAsState()

    ShowSignOutDialog(show = showSignOutDialog, onDismiss = {
        showSignOutDialog = false
    }, onSignOut = {
        viewModel.logoutUser(navController = navController)
    })
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
    ) {
        if (isDashBoard) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            ) {
                Text(
                    text = stringResource(R.string.hello_david),
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = stringResource(R.string.have_a_nice_day),
                    style = MaterialTheme.typography.subtitle1
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = "back button",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            ProfileView(
                name = "${userModel.value?.firstName ?: ""} ${userModel.value?.lastName ?: ""}",
                onClick = {
                    expanded = true
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    /* Handle refresh! */
                    expanded = false

                }) {
                    Text("Profile")
                }
                DropdownMenuItem(onClick = {
                    /* Handle settings! */
                    expanded = false
                }) {
                    Text("Settings")
                }
                Divider()
                DropdownMenuItem(onClick = {
                    expanded = false
                    showSignOutDialog = true
                }) {
                    Text("Sign out")
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = { /*TODO*/ }) {
                BadgedBox(badge = {
                    Badge() {
                        Text(
                            "90",
                            style = MaterialTheme.typography.caption.copy(
                                fontSize = 7.sp,
                                color = Color.White
                            )
                        )
                    }
                }) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = "notifications",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = "notifications",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun ShowSignOutDialog(show: Boolean, onDismiss: () -> Unit, onSignOut: () -> Unit) {
    if (show) {
        AlertDialog(
            onDismissRequest = {

            },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                })
                {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.button
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                    onSignOut()
                })
                {
                    Text(
                        text = stringResource(R.string.ok),
                        style = MaterialTheme.typography.button
                    )
                }
            },
            title = { Text(text = "Sign out?") },
            text = { Text(text = "Are you sure want to sign out?") }
        )
    }
}

@Composable
fun ProfileView(
    name: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        elevation = 8.dp,
        shape = CircleShape,
        color = MaterialTheme.colors.onSecondary
    ) {
        Row(
            modifier = Modifier.wrapContentWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_outline_account_circle_24),
                contentDescription = "Account",
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp),
                tint = BlueLight
            )
//            AsyncImage(
//                model = painterResource(R.drawable.ic_outline_account_circle_24),
//                contentDescription = "profile image",
//                contentScale = ContentScale.Crop,
//                placeholder = painterResource(R.drawable.ic_outline_account_circle_24),
//                modifier = Modifier
//                    .padding(4.dp)
//                    .clip(CircleShape)
//                    .width(30.dp)
//                    .height(30.dp)
//            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.body2.copy(fontSize = 10.sp),
                color = Color.Black,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
    }


}