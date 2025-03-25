package com.example.pushnotificationsapp.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("jobs", "Servicios", Icons.Default.Home),
    BottomNavItem("step_journey", "Podómetro", Icons.Default.Favorite)
)