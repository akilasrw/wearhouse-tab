package com.aeroclubcargo.warehouse.presentation.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aeroclubcargo.warehouse.R

//@Preview(showBackground = true)
@Composable
fun GetTopBar(userName: String, profileUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
    ) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            ProfileView(
                name = userName,
                profileUrl = profileUrl,
                onClick = {
                    // TODO
                }
            )
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
fun ProfileView(
    name: String,
    profileUrl: String,
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
            AsyncImage(
                model = profileUrl,
                contentDescription = "profile image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_outline_account_circle_24),
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .width(30.dp)
                    .height(30.dp)
            )
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