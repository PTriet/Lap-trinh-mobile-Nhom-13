package com.example.bookinghotel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.bookinghotel.ui.theme.BookingHotelTheme


//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            BookingHotelTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    HotelBookingScreen(
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookingHotelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // Có thể lấy context từ đây nếu muốn Toast
                    val context = LocalContext.current

                    BookingFormScreen(
                        modifier = Modifier.padding(innerPadding),
                        onBookingConfirmed = { name, phone, checkIn, checkOut ->
                            Toast.makeText(
                                context,
                                "Booking by $name\nFrom $checkIn to $checkOut",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )

                }
            }
        }
    }
}


