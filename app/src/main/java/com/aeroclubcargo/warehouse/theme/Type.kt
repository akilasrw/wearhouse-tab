package com.aeroclubcargo.warehouse.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.aeroclubcargo.warehouse.R

// Set of Material typography styles to start with
val outFitFontFamily = FontFamily(
    Font(R.font.outfit_regular),
    Font(R.font.outfit_bold, weight = FontWeight.Bold),
    Font(R.font.outfit_extra_bold, weight = FontWeight.ExtraBold),
    Font(R.font.outfit_black, weight = FontWeight.Black),
    Font(R.font.outfit_extra_light, weight = FontWeight.ExtraLight),
    Font(R.font.outfit_light, weight = FontWeight.Light),
    Font(R.font.outfit_medium, weight = FontWeight.Medium),
    Font(R.font.outfit_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.outfit_thin, weight = FontWeight.Thin),
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp,
        color = Black
    ),
    h2 = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp,
        color = Black
    ),
    h3 = TextStyle(
        fontFamily = outFitFontFamily,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        letterSpacing = 0.sp,
        color = Black
    ),
    h4 = TextStyle(
        fontFamily = outFitFontFamily,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp,
        color = Black
    ),
    h5 = TextStyle(
        fontFamily = outFitFontFamily,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        color = Black
    ),
    h6 = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
        color = Black
    ),
    subtitle1 = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        color = Gray1
    ),
    subtitle2 = TextStyle(
        fontFamily = outFitFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp,
        color = Gray1
    ),
    body1 = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        color = Black
    ),
    body2 = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        color = Black
    ),
    button = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        color = Black
    ),
    overline = TextStyle(
        fontFamily = outFitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
        color = Black
    )
)