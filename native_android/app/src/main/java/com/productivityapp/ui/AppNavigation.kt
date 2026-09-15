package com.productivityapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Focus : Screen("focus")
    object Tasks : Screen("tasks")
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Focus.route) {
        composable(Screen.Focus.route) {
            // TODO: Add Focus Screen
        }
        composable(Screen.Tasks.route) {
            // TODO: Add Tasks Screen
        }
        composable(Screen.Settings.route) {
            // TODO: Add Settings Screen
        }
    }
}
