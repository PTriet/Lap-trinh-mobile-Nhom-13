package com.example.bookinghotel

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapp.InformationScreen
import com.example.myapp.DeviceSettingsScreen
import com.example.myapp.UserMenuScreen
import com.example.myapp.PersonalInfoScreen

fun NavGraphBuilder.setupNavGraph(navController: NavController, innerPadding: PaddingValues) {
    composable("home") { HotelBookingScreen(navController, innerPadding) }
    composable("recent") { RecentScreen(navController, innerPadding) }
    composable("saved") { SavedScreen(navController, innerPadding) }
    composable("prioritize") { UserMenuScreen(navController, innerPadding) }
    composable("signup") { SignUpScreen(navController, innerPadding) }
    composable("login") { LoginScreen(navController, innerPadding) }
    composable("information?username={username}") { backStackEntry ->
        // Lấy giá trị tham số 'username' từ route
        val username = backStackEntry.arguments?.getString("username") ?: "User"
        InformationScreen(navController, innerPadding, username) // Truyền username vào InformationScreen
    }
    composable("personal_info_screen?email={email}") { backStackEntry ->
        // Lấy giá trị tham số 'email' từ route
        val email = backStackEntry.arguments?.getString("email") ?: "N/A"
        PersonalInfoScreen(navController, innerPadding, email) // Truyền email vào PersonalInfoScreen
    }
    composable("inforhotel") { InfoHotel(navController, innerPadding) }
    composable("inforroom") { InfoRoom(navController, innerPadding) }
    composable("setting") { DeviceSettingsScreen(navController, innerPadding) }
    composable("booking") { BookingFormWrapperScreen(navController, innerPadding) }
    composable("security"){ SecurityCenterScreen(navController, innerPadding) }
    composable("contact"){ ContactCustomerServiceScreen(navController, innerPadding) }
    composable("offer"){ OfferScreen(navController, innerPadding) }
}