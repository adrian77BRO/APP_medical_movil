package com.example.pushnotificationsapp.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pushnotificationsapp.appointment.presentation.FormScreen
import com.example.pushnotificationsapp.appointment.presentation.HistoryScreen
import com.example.pushnotificationsapp.job.presentation.ListJobsScreen
import com.example.pushnotificationsapp.login.presentation.LoginScreen
import com.example.pushnotificationsapp.register.presentation.RegisterScreen
import com.example.pushnotificationsapp.stepcounter.presentation.StepHistoryScreen
import com.example.pushnotificationsapp.stepcounter.presentation.StepJourneyScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    val route = currentRoute(navController)
    val showBottomBar = route in listOf("jobs", "step_journey")

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.White
                ) {
                    val currentRoute = currentRoute(navController)
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = Color(0xFF3ECF72)
                            ) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black,
                                selectedTextColor = Color.Black,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color(0xFFDDDDDD)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    onNavigateToServices = {
                        navController.navigate("jobs") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate("register") }
                )
            }
            composable("register") {
                RegisterScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("jobs") {
                ListJobsScreen(
                    onNavigateToHistory = { idJob ->
                        navController.navigate("history/$idJob")
                    }
                )
            }
            composable("history/{idJob}") { backStackEntry ->
                val idJob = backStackEntry.arguments?.getString("idJob")?.toIntOrNull() ?: 0
                HistoryScreen(
                    idJob = idJob,
                    onNavigateToReservation = { id ->
                        navController.navigate("reserve/$id")
                    }
                )
            }
            composable("reserve/{idJob}") { backStackEntry ->
                val idJob = backStackEntry.arguments?.getString("idJob")?.toIntOrNull() ?: 0
                FormScreen(
                    idJob = idJob,
                    onReservationSuccess = { navController.popBackStack() },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("step_journey") {
                StepJourneyScreen(navController = navController)
            }
            composable("step_history") {
                StepHistoryScreen()
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}