package com.example.bookinghotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bookinghotel.components.BottomNavigationBar
import com.example.bookinghotel.ui.theme.HotelBookingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelBookingTheme {
                val navController = rememberNavController()
                val context = applicationContext
                val isLoggedIn by UserPreferences.isLoggedIn(context).collectAsState(initial = false)

                Scaffold(
                    contentWindowInsets = WindowInsets(0),
                    bottomBar = {
                        val currentRoute = navController.currentBackStackEntryFlow
                            .collectAsState(initial = navController.currentBackStackEntry)
                            .value?.destination?.route

                        val selectedItemIndex = when (currentRoute) {
                            "home" -> 0
                            "recent" -> 1
                            "saved" -> 2
                            "prioritize" -> 3
                            else -> -1
                        }

                        if (selectedItemIndex != -1) {
                            BottomNavigationBar(
                                navController = navController,
                                selectedItem = selectedItemIndex
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        setupNavGraph(
                            navController = navController,
                            innerPadding = innerPadding,
                            isLoggedIn = isLoggedIn
                        )
                    }
                }
            }
        }
    }
}