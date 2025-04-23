package com.example.bookinghotel

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun BookingFormWrapperScreen(navController: NavController, paddingValues: PaddingValues) {
    val context = LocalContext.current

    BookingFormScreen(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        onBookingConfirmed = { name, phone, checkIn, checkOut ->
            Toast.makeText(
                context,
                "Đặt phòng bởi $name\nTừ $checkIn đến $checkOut",
                Toast.LENGTH_LONG
            ).show()
            navController.popBackStack()
        }
    )
}
