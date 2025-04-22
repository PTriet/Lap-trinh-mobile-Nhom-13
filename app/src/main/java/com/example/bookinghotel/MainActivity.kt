package com.example.bookinghotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bookinghotel.ui.theme.HotelBookingTheme
import com.example.bookinghotel.components.BottomNavigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @OptIn(ExperimentalMaterial3Api::class)
        setContent {
            HotelBookingTheme {
                val navController = rememberNavController()

                // Xác định selectedItem dựa trên route hiện tại
                val currentRoute = navController
                    .currentBackStackEntryFlow
                    .collectAsState(initial = navController.currentBackStackEntry)
                    .value?.destination?.route

                val selectedItemIndex = when (currentRoute) {
                    "home" -> 0
                    "recent" -> 1
                    "saved" -> 2
                    "prioritize" -> 3
                    else -> -1 // Tab không thuộc BottomNav
                }

                Scaffold(
                    contentWindowInsets = WindowInsets(0),
                    bottomBar = {
                        // Chỉ hiển thị BottomBar khi đang ở route hợp lệ
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
                        startDestination = "home", // hoặc "login" nếu có màn login
                    ) {
                        setupNavGraph(navController, innerPadding)
                    }
                }
            }
        }
    }
}