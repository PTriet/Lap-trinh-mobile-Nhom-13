package com.example.bookinghotel

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.net.Uri

@Composable
fun InfoHotel(
    navController: NavController,
    paddingValues: PaddingValues,
    name: String,
    address: String,
    price: String,
    rating: String
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Image(
            painter = painterResource(R.drawable.hotel1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(3f / 4f)
                .height(150.dp),
            contentScale = ContentScale.Crop
        )

        Card(
            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
            backgroundColor = Color.White,
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .fillMaxHeight(3f / 5f)
        ) {
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(address, fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    repeat(5) {
                        Icon(Icons.Default.Star, contentDescription = "Star", tint = Color(0xFFFFD700))
                    }
                    Text("5.0 ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 5.dp, top = 5.dp))
                    Text(" - 1231 Reviews", fontWeight = FontWeight.W300, modifier = Modifier.padding(start = 2.dp, top = 5.dp))
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text("Description", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Hotel Room means an area that is designed and constructed to be occupied by one or more persons...",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
                Text("Read more", color = Color.Blue)
                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(4) { index ->
                        Image(
                            painter = painterResource(R.drawable.hotel2),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(end = if (index < 3) 8.dp else 0.dp)
                                .clickable {
                                    navController.navigate("inforroom")
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 50.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(color = Color.LightGray, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            val user = auth.currentUser
                            if (user != null) {
                                saveHotel(db, user.uid, name, address, price, rating, context)
                            } else {
                                Toast.makeText(context, "Vui lòng đăng nhập để lưu!", Toast.LENGTH_SHORT).show()
                                navController.navigate("login?pendingSave=true")
                            }
                        }
                    ) {
                        Icon(Icons.Default.Bookmark, contentDescription = "Bookmark Icon", modifier = Modifier.size(24.dp))
                    }
                }

                Button(
                    onClick = {
                        Log.d("InfoHotel", "Navigating to booking screen")
                        navController.navigate(
                            "bookingform/${Uri.encode(name)}/${Uri.encode(address)}/${Uri.encode(price)}/${Uri.encode(rating)}"
                        )                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(220.dp)
                        .height(55.dp)
                ) {
                    Text("Book Now")
                }
            }
        }
    }
}

fun saveHotel(
    db: FirebaseFirestore,
    uid: String,
    name: String,
    address: String,
    price: String,
    rating: String,
    context: android.content.Context
) {
    val hotelData = hashMapOf(
        "name" to name,
        "address" to address,
        "price" to price,
        "rating" to rating
    )

    db.collection("users")
        .document(uid)
        .collection("savedHotels")
        .add(hotelData)
        .addOnSuccessListener {
            Toast.makeText(context, "Đã lưu khách sạn!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Lưu thất bại!", Toast.LENGTH_SHORT).show()
        }
}