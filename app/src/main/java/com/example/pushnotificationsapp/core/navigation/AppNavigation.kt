package com.example.pushnotificationsapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pushnotificationsapp.job.presentation.ServicesScreen
import com.example.pushnotificationsapp.login.presentation.LoginScreen
import com.example.pushnotificationsapp.register.presentation.RegisterScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToServices = { navController.navigate("services") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("services") {
            ServicesScreen()
        }
    }
}

/*import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pushnotificationsapp.login.presentation.LoginScreen
import com.example.pushnotificationsapp.register.presentation.RegisterScreen
import com.example.pushnotificationsapp.appointment.presentation.TaskFormScreen
import com.example.pushnotificationsapp.appointment.presentation.TaskListScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { username ->
                    navController.navigate("taskList") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        composable("taskList") {
            TaskListScreen(
                onTaskClick = { task ->
                    // Aquí podrías navegar a una pantalla de detalle de tarea si lo requieres.
                },
                onAddTask = {
                    navController.navigate("taskForm")
                }
            )
        }
        composable("taskForm") {
            TaskFormScreen(
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}*/