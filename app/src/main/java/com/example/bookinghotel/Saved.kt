package com.example.bookinghotel

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
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
fun SavedScreen(navController: NavController, paddingValues: PaddingValues) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    var savedHotels by remember { mutableStateOf(listOf<Pair<String, Map<String, String>>>()) }
    var hotelToDelete by remember { mutableStateOf<Pair<String, Map<String, String>>?>(null) }

    LaunchedEffect(user) {
        if (user != null) {
            db.collection("users")
                .document(user.uid)
                .collection("savedHotels")
                .get()
                .addOnSuccessListener { documents ->
                    val hotels = documents.map { doc ->
                        doc.id to mapOf(
                            "name" to (doc.getString("name") ?: ""),
                            "address" to (doc.getString("address") ?: ""),
                            "price" to (doc.getString("price") ?: ""),
                            "rating" to (doc.getString("rating") ?: "")
                        )
                    }
                    savedHotels = hotels
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Đã lưu",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (savedHotels.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
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
                            .offset(x = 110.dp, y = (-190).dp)
                            .size(48.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Heart",
                            tint = Color.Magenta
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Lưu chỗ nghỉ bạn yêu thích",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Tạo danh sách các chỗ nghỉ bạn yêu thích",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 24.dp)
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

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = { navController.navigate("home") }
                    ) {
                        Text("Tạo một danh sách", color = Color.Blue)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                savedHotels.forEach { (docId, hotel) ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color(0xFFE0E0E0),
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate(
                                    "inforhotel/${Uri.encode(hotel["name"] ?: "")}" +
                                            "/${Uri.encode(hotel["address"] ?: "")}" +
                                            "/${Uri.encode(hotel["price"] ?: "")}" +
                                            "/${Uri.encode(hotel["rating"] ?: "")}"
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
                                    text = hotel["name"] ?: "Unknown Hotel",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = hotel["price"] ?: "",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            IconButton(onClick = {
                                hotelToDelete = docId to hotel
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }

    // AlertDialog xác nhận xoá
    if (hotelToDelete != null) {
        val (docId, _) = hotelToDelete!!
        AlertDialog(
            onDismissRequest = { hotelToDelete = null },
            title = { Text("Xác nhận xoá") },
            text = { Text("Bạn có chắc chắn muốn xoá khách sạn này không?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        user?.let { u ->
                            db.collection("users")
                                .document(u.uid)
                                .collection("savedHotels")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    savedHotels = savedHotels.filterNot { it.first == docId }
                                    hotelToDelete = null
                                }
                        }
                    }
                ) {
                    Text("Xoá", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { hotelToDelete = null }) {
                    Text("Huỷ")
                }
            }
        )
    }
}