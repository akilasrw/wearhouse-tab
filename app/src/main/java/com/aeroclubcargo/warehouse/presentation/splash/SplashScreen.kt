package com.aeroclubcargo.warehouse.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.silentLogin(navController = navController)
//        delay(4000)
//        navController.popBackStack()
//        navController.navigate(Screen.LoginScreen.route)
    }
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.light_blue))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Pulsating(
            color = colorResource(id = R.color.light_blue_opacity_l1),
            scale = scaleInfiniteTransition(targetValue = 1.8f, durationMillis = 1000)
        )
        Pulsating(
            color = colorResource(id = R.color.light_blue_opacity_l2),
            scale = scaleInfiniteTransition(targetValue = 1.6f, durationMillis = 1000)
        )
        Pulsating(
            color = colorResource(id = R.color.light_blue_opacity_l3),
            scale = scaleInfiniteTransition(targetValue = 1.4f, durationMillis = 1000)
        )

        MiddleBanner()
        Text(
            text = stringResource(R.string.skytech_software_solutions_pvt_ltd),
            color = colorResource(id = R.color.light_text_color),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
        )

    }
}

@Composable
fun scaleInfiniteTransition(
    initialValue: Float = 1f,
    targetValue: Float,
    durationMillis: Int,
): Float {
    val infiniteTransition = rememberInfiniteTransition()
    val scale: Float by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    return scale
}


@Composable
fun Pulsating(color: Color, scale: Float) {
    Box(modifier = Modifier.scale(scale), contentAlignment = Alignment.Center) {
        Surface(
            color = color,
            shape = CircleShape,
            modifier = Modifier.size(300.dp),
            content = {


            }
        )
    }
}

@Composable
fun MiddleBanner() {
    val image = painterResource(id = R.drawable.ic_logo_skytech)
    Image(
        painter = image,
        contentDescription = "Splash Wave ",
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .height(60.dp)
    )
}