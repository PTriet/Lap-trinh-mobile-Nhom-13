package com.example.bookinghotel

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapp.InformationScreen
import com.example.myapp.UserMenuScreen
import com.example.myapp.DeviceSettingsScreen
import com.example.myapp.PersonalInfoScreen
import com.google.firebase.auth.FirebaseAuth

fun NavGraphBuilder.setupNavGraph(
    navController: NavController,
    innerPadding: PaddingValues,
    isLoggedIn: Boolean
) {
    composable("root") {
        LaunchedEffect(isLoggedIn) {
            if (isLoggedIn) {
                navController.navigate("home") {
                    popUpTo("root") { inclusive = true }
                }
            } else {
                navController.navigate("prioritize") {
                    popUpTo("root") { inclusive = true }
                }
            }
        }
    }

    composable("home") { HotelBookingScreen(navController, innerPadding) }
    composable("recent") { RecentScreen(navController, innerPadding) }
    composable("saved") { SavedScreen(navController, innerPadding) }

    composable("prioritize") {
        val context = LocalContext.current
        val isLoggedIn by UserPreferences.isLoggedIn(context).collectAsState(initial = false)
        val auth = FirebaseAuth.getInstance()

        if (isLoggedIn) {
            val username = auth.currentUser?.email?.substringBefore("@") ?: "User"
            InformationScreen(navController, innerPadding, username)
        } else {
            UserMenuScreen(navController, innerPadding)
        }
    }

    composable(
        "login?pendingSave={pendingSave}",
        arguments = listOf(
            navArgument("pendingSave") {
                defaultValue = false
                type = NavType.BoolType
            }
        )
    ) { backStackEntry ->
        val pendingSave = backStackEntry.arguments?.getBoolean("pendingSave") ?: false
        LoginScreen(navController, innerPadding, pendingSave)
    }

    composable("signup") { SignUpScreen(navController, innerPadding) }

    composable(
        "information?username={username}",
        arguments = listOf(
            navArgument("username") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val username = backStackEntry.arguments?.getString("username") ?: "User"
        InformationScreen(navController, innerPadding, username)
    }

    composable(
        "personal_info_screen?email={email}",
        arguments = listOf(
            navArgument("email") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val email = backStackEntry.arguments?.getString("email") ?: "N/A"
        PersonalInfoScreen(navController, innerPadding, email)
    }

    composable("setting") {
        DeviceSettingsScreen(navController, innerPadding)
    }

    composable(
        "bookingform/{name}/{address}/{price}/{rating}",
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
        BookingFormWrapperScreen(
            navController,
            innerPadding,
            name,
            address,
            price,
            rating
        )
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

    composable("inforroom") {
        InfoRoom(navController, innerPadding)
    }
    composable("contact") {
        ContactCustomerServiceScreen(navController, innerPadding)
    }
    composable("security") {
        SecurityCenterScreen(navController, innerPadding)
    }
    composable("offer") {
        OfferScreen(navController, innerPadding)
    }
}

