package com.cyanideph.java.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cyanideph.java.ui.screens.HomeScreen
import com.cyanideph.java.ui.screens.LoginScreen

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
}

@Composable
fun UzzapNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLogin = { navController.navigate(Routes.HOME) }
            )
        }
        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}
