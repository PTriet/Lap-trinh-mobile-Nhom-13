package com.example.bookinghotel

import Entity.User
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.NonNull
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bookinghotel.ui.theme.HotelBookingTheme
import com.example.bookinghotel.components.BottomNavigationBar
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private val aut = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
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
        var email: String= "Quankietsuat@gmail.com"
        var password: String = "123456789"
        aut.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            task -> if (task.isSuccessful) {
            val uid = aut.currentUser?.uid ?: return@addOnCompleteListener

//            db.collection("users").document(uid).get()
//                .addOnSuccessListener { doc ->
//                    val user = doc.toObject(User::class.java)
//                    if (user != null) {
//                        Log.d("Main","createUserWithEmail:success")
//                    } else {
//                        onFailure("Không tìm thấy thông tin người dùng.")
//                    }
//                }
//                .addOnFailureListener { e -> onFailure(e.message ?: "Lỗi lấy dữ liệu") }
//
//        } else {
//            onFailure(task.exception?.message ?: "Đăng nhập thất bại")
        }

        }
    }
}

