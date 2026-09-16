package com.productivityapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.productivityapp.TaskViewModel
import com.productivityapp.ui.screens.TaskDetailScreen
import com.productivityapp.ui.screens.KnowledgeGraphCanvas
import com.productivityapp.ui.screens.FocusModeScreen
import com.productivityapp.ui.screens.SettingsScreen
import androidx.compose.runtime.collectAsState

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: String) = "task_detail/$taskId"
    }
    object Graph : Screen("graph")
    object FocusMode : Screen("focus_mode/{taskId}") {
        fun createRoute(taskId: String) = "focus_mode/$taskId"
    }
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation(
    taskViewModel: TaskViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            com.productivityapp.MainAppScreen(
                taskViewModel = taskViewModel,
                onNavigateToDetail = { taskId ->
                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                },
                onNavigateToGraph = {
                    navController.navigate(Screen.Graph.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: return@composable
            TaskDetailScreen(
                taskId = taskId,
                viewModel = taskViewModel,
                onBack = { navController.popBackStack() },
                onStartFocus = { id ->
                    navController.navigate(Screen.FocusMode.createRoute(id))
                }
            )
        }
        composable(Screen.Graph.route) {
            val tasks = taskViewModel.tasks.collectAsState(initial = emptyList()).value
            KnowledgeGraphCanvas(
                tasks = tasks,
                edges = emptyList(),
                onNodeTapped = { taskId ->
                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                }
            )
        }
        composable(
            route = Screen.FocusMode.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: return@composable
            val task = taskViewModel.getTaskById(taskId)
            FocusModeScreen(
                taskId = taskId,
                taskTitle = task?.title ?: "Unknown Task",
                onFinish = { navController.popBackStack() }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
