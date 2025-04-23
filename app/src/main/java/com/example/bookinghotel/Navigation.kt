package com.example.bookinghotel

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapp.InformationScreen
import com.example.myapp.DeviceSettingsScreen
import com.example.myapp.UserMenuScreen

fun NavGraphBuilder.setupNavGraph(navController: NavController, innerPadding: PaddingValues) {
    composable("home") { HotelBookingScreen(navController, innerPadding) }
    composable("recent") { RecentScreen(navController, innerPadding) }
    composable("saved") { SavedScreen(navController, innerPadding) }
    composable("prioritize") { UserMenuScreen(navController, innerPadding) }
    composable("signup"){ SignUpScreen(navController, innerPadding) }
    composable("inforhotel"){ InfoHotel(navController,innerPadding) }
    composable("inforroom"){ InfoRoom(navController, innerPadding) }
    composable("setting"){ DeviceSettingsScreen(navController, innerPadding) }
    composable("booking"){ BookingFormWrapperScreen(navController, innerPadding) }
}