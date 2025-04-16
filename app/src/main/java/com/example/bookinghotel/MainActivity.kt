package com.example.bookinghotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.bookinghotel.ui.theme.BookingHotelTheme
import com.example.myapp.UserMenuScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookingHotelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserMenuScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

}




