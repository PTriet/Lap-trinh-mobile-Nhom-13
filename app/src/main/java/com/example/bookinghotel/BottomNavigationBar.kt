package com.example.bookinghotel.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.bookinghotel.navigation.NavigationItem

@Composable
fun BottomNavigationBar(navController: NavController, selectedItem: Int) {
    NavigationBar(containerColor = Color(0xFFF1F0F6)) {
        NavigationItem.all.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                alwaysShowLabel = false
            )
        }
    }
}