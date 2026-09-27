package com.cyanideph.java.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val UzzapColors = lightColorScheme(
    primary = Color(0xFF159447),
    onPrimary = Color.White,
    secondary = Color(0xFF5E35B1),
    background = Color.White,
    surface = Color.White
)

@Composable
fun UzzapTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = UzzapColors,
        content = content
    )
}
