package com.example.pushnotificationsapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pushnotificationsapp.appointment.presentation.HistoryScreen
import com.example.pushnotificationsapp.job.presentation.ListJobsScreen
import com.example.pushnotificationsapp.login.presentation.LoginScreen
import com.example.pushnotificationsapp.register.presentation.RegisterScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToServices = { navController.navigate("jobs") {
                    popUpTo("login") { inclusive = true }
                } },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("jobs") {
            ListJobsScreen(onNavigateToHistory = { idJob ->
                navController.navigate("history/$idJob")
            })
        }
        composable("history/{idJob}") { backStackEntry ->
            val idJob = backStackEntry.arguments?.getString("idJob")?.toIntOrNull()
            HistoryScreen(idJob = idJob ?: 0)
        }
    }
}