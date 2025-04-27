package com.example.bookinghotel

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RecentScreen(navController: NavController, paddingValues: PaddingValues) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    var recentBookings by remember { mutableStateOf(listOf<Pair<String, Map<String, String>>>()) } // (docId, data)
    var bookingToDelete by remember { mutableStateOf<Pair<String, Map<String, String>>?>(null) }

    LaunchedEffect(user) {
        if (user != null) {
            db.collection("users")
                .document(user.uid)
                .collection("recentBookings")
                .get()
                .addOnSuccessListener { documents ->
                    val bookings = documents.map { doc ->
                        doc.id to mapOf(
                            "name" to (doc.getString("name") ?: ""),
                            "address" to (doc.getString("address") ?: ""),
                            "price" to (doc.getString("price") ?: ""),
                            "rating" to (doc.getString("rating") ?: "")
                        )
                    }
                    recentBookings = bookings
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Gần đây",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (recentBookings.isEmpty()) {
            // 🔥 Chỉ hiện UI cũ khi KHÔNG CÓ booking nào
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        backgroundColor = Color(0xFFE0E0E0),
                        elevation = 4.dp,
                        modifier = Modifier
                            .width(240.dp)
                            .height(180.dp)
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.hotel1),
                                contentDescription = "Hotel Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Hotel\nDownTown",
                                modifier = Modifier.padding(start = 12.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "$300.00",
                                modifier = Modifier.padding(start = 12.dp),
                                color = Color.Gray
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .offset(x = 110.dp, y = (-70).dp)
                            .size(48.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "Recent",
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Địa điểm bạn đã tới",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Chưa có địa điểm đã tới",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0099FF)),
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .height(48.dp)
                ) {
                    Text("Bắt đầu tìm kiếm", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            // 🔥 Nếu có booking thì show danh sách các booking
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                recentBookings.forEach { (docId, booking) ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color(0xFFE0E0E0),
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate(
                                    "inforhotel/${Uri.encode(booking["name"] ?: "")}" +
                                            "/${Uri.encode(booking["address"] ?: "")}" +
                                            "/${Uri.encode(booking["price"] ?: "")}" +
                                            "/${Uri.encode(booking["rating"] ?: "")}"
                                )
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = booking["name"] ?: "Unknown Hotel",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = booking["price"] ?: "",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            IconButton(onClick = {
                                bookingToDelete = docId to booking
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialog xác nhận xoá
    if (bookingToDelete != null) {
        val (docId, _) = bookingToDelete!!
        AlertDialog(
            onDismissRequest = { bookingToDelete = null },
            title = { Text("Xác nhận xoá") },
            text = { Text("Bạn có chắc chắn muốn xoá booking này không?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        user?.let { u ->
                            db.collection("users")
                                .document(u.uid)
                                .collection("recentBookings")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    recentBookings = recentBookings.filterNot { it.first == docId }
                                    bookingToDelete = null
                                }
                        }
                    }
                ) {
                    Text("Xoá", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { bookingToDelete = null }) {
                    Text("Huỷ")
                }
            }
        )
    }
}