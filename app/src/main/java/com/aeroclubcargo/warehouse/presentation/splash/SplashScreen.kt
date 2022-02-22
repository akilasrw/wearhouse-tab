package com.aeroclubcargo.warehouse.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.R
import com.aeroclubcargo.warehouse.presentation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
) {

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 100, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        navController.navigate(Screen.LoginScreen.route)
    }

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.light_blue))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Pulsating(pulseFraction = 1.8f, color = colorResource(id = R.color.light_blue_opacity_l2))
        Pulsating(pulseFraction = 1.2f, color = colorResource(id = R.color.light_blue_opacity_l1))
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
fun Pulsating(pulseFraction: Float = 1.2f, color: Color) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale1 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Box(modifier = Modifier.scale(scale1), contentAlignment = Alignment.Center) {
        Surface(
            color = color, //colorResource(id = R.color.light_blue_opacity_l1),
            shape = CircleShape,
            modifier = Modifier.size(250.dp),
            content = {

            }
        )
    }
}

//@Composable
//fun Pulsating(pulseFraction: Float = 1.2f) {
//
//
//    val infiniteTransition = rememberInfiniteTransition()
//
//    val scale1 by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = pulseFraction,
//        animationSpec = infiniteRepeatable(
//            animation = tween(2000, easing = LinearOutSlowInEasing),
//            repeatMode = RepeatMode.Restart
//        )
//    )
//    val scale2 by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = pulseFraction,
//        animationSpec =  infiniteRepeatable(
//            animation = tween(2000, easing = LinearOutSlowInEasing),
//            repeatMode = RepeatMode.Restart,
//        )
//    )
//    val scale3 by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = pulseFraction,
//        animationSpec = infiniteRepeatable(
//            animation = tween(2000, easing = LinearOutSlowInEasing),
//            repeatMode = RepeatMode.Restart
//        )
//    )
//
//    Box(modifier = Modifier.scale(scale1),contentAlignment = Alignment.Center) {
//        Surface(
//            color = colorResource(id = R.color.light_blue_opacity_l1),
//            shape = CircleShape,
//            modifier = Modifier.size(200.dp),
//            content = {
//
//            }
//        )
//        Box(modifier = Modifier.scale(scale2),contentAlignment = Alignment.Center) {
//            Surface(
//                color = colorResource(id = R.color.light_blue_opacity_l2),
//                shape = CircleShape,
//                modifier = Modifier.size(150.dp),
//                content = {
//
//                }
//            )
//            Box(modifier = Modifier.scale(scale3),contentAlignment = Alignment.Center) {
//                Surface(
//                    color = colorResource(id = R.color.light_blue_opacity_l3),
//                    shape = CircleShape,
//                    modifier = Modifier.size(100.dp),
//                    content = {
//
//                    }
//                )
//            }
//        }
//    }
//}

@Composable
fun MiddleBanner() {
    val image = painterResource(id = R.drawable.ic_logo_skytech)
    Image(
        painter = image,
        contentDescription = "Splash Wave",
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .height(60.dp)
    )
}