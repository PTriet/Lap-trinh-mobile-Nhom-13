package com.example.bookinghotel.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : NavigationItem("home", Icons.Default.Home, "Home")
    object Recent : NavigationItem("recent", Icons.Default.AccessTime, "Recent")
    object Saved : NavigationItem("saved", Icons.Default.FavoriteBorder, "Saved")
    object Prioritize : NavigationItem("prioritize", Icons.Default.Settings, "Setting")

    companion object {
        val all = listOf(Home, Recent, Saved, Prioritize)
    }
}