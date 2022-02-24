package com.aeroclubcargo.warehouse.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

/*private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)*/

private val LightColorPalette = lightColors(
    primary = BlueLight,
    surface = White,

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