package com.death.monkey.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = darkColors(
    primary = orange,
    primaryVariant = orange80,
    secondary = Color.White,
    background = orange30,
    surface = orange
)

@Composable
fun MonkeyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}