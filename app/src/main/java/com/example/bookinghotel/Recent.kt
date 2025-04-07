package com.example.bookinghotel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun RecentScreen(modifier : Modifier = Modifier){
    Scaffold(
        backgroundColor = Color.White,
        bottomBar = {
            //Menu
            BottomNavigation(
                backgroundColor = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                BottomNavigationItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
                )
                BottomNavigationItem(
                    selected = true,
                    onClick = {},
                    icon ={
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFD1C4E9)),
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Recent",
                                tint = Color(0xFF1E1E1E)
                            )
                        }
                    }
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Heart") }
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
                )
            }
        }
    ){
            innerPadding ->
        Column(

            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            //Tieu de
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(vertical = 16.dp)
            ){
                Text(
                    text = "Gần đây",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Hình + đồng hồ
                Box(
                    modifier = Modifier
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        backgroundColor = Color(0xFFE0E0E0),
                        elevation = 4.dp,
                        modifier = Modifier
                            .width(240.dp)
                            .height(160.dp)
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
                            .clip(CircleShape)
                            .background(Color.Gray),
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

                // TIÊU ĐỀ PHỤ
                Text(
                    text = "Địa điểm bạn đã tới",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Chưa có địa điểm đã đến",
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
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0099FF)),
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .align(Alignment.CenterHorizontally)
                        .height(48.dp)
                ) {
                    Text("Bắt đầu tìm kiếm", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Tạo một danh sách", color = Color.Blue)
                }
            }
        }
    }
}