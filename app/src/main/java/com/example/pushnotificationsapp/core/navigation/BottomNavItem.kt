package com.example.pushnotificationsapp.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("jobs", "Servicios", Icons.Default.Home),
    BottomNavItem("steps", "Contador", Icons.Default.Face)
)