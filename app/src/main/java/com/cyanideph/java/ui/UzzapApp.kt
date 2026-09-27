package com.cyanideph.java.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
            UzzapNavHost(navController)
        }
    }
}
