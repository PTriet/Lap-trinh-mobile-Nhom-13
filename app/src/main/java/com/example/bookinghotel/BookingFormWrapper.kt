package com.example.bookinghotel

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BookingFormWrapperScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    nameHotel: String,
    addressHotel: String,
    priceHotel: String,
    ratingHotel: String
) {
    BookingFormScreen(
        navController = navController,
        nameHotel = nameHotel,
        addressHotel = addressHotel,
        priceHotel = priceHotel,
        ratingHotel = ratingHotel,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        onBackPressed = {
            navController.popBackStack()
        }
    )
}