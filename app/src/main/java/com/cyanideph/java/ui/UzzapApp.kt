package com.cyanideph.java.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.cyanideph.java.ui.navigation.UzzapNavHost
import com.cyanideph.java.ui.theme.UzzapTheme

@Composable
fun UzzapApp() {
    val navController = rememberNavController()
    UzzapTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Keep the legacy Uzzap chrome inside Android's safe system-bar area.
            // This prevents the title bar from entering the status bar and the
            // bottom function bar from being hidden behind the navigation/gesture bar.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                UzzapNavHost(navController)
            }
        }
    }
}
