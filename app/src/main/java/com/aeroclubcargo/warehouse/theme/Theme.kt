package com.aeroclubcargo.warehouse.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)*/

private val LightColorPalette = lightColors(
    primary = BlueLight,
    surface = White,
    background = backgroundLightBlue,
    onSecondary = Color.White,
    secondary = Color.Black,
    onSurface = BlueLight3
)

@Composable
fun SkyTechCargoTheme( content: @Composable () -> Unit) {
    val colors = LightColorPalette
    // since no dark theme we are using only light theme
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}