package com.example.bookinghotel

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        val username = backStackEntry.arguments?.getString("username") ?: "User"
        InformationScreen(navController, innerPadding, username)
    }
    composable("personal_info_screen?email={email}") { backStackEntry ->
        val email = backStackEntry.arguments?.getString("email") ?: "N/A"
        PersonalInfoScreen(navController, innerPadding, email)
    }
    composable("booking") {
        BookingFormWrapperScreen(navController, innerPadding)
    }
    composable(
        "inforhotel/{name}/{address}/{price}/{rating}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("address") { type = NavType.StringType },
            navArgument("price") { type = NavType.StringType },
            navArgument("rating") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
        val address = backStackEntry.arguments?.getString("address") ?: "Unknown"
        val price = backStackEntry.arguments?.getString("price") ?: "Unknown"
        val rating = backStackEntry.arguments?.getString("rating") ?: "Unknown"
        InfoHotel(navController, innerPadding, name, address, price, rating)
    }
}
