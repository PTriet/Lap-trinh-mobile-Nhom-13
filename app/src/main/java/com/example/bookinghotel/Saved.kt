package com.example.bookinghotel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun SavedScreen(modifier : Modifier = Modifier){
    val selectedItem = remember { mutableStateOf(2) }

    androidx.compose.material3.Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFF1F0F6)) {
                val items = listOf(
                    Icons.Default.Home,
                    Icons.Default.AccessTime,
                    Icons.Default.FavoriteBorder,
                    Icons.Default.Person
                )

                items.forEachIndexed { index, icon ->
                    val isSelected = selectedItem.value == index
                    val isSaved = index == 2

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedItem.value = index },
                        icon = {
                            if (isSaved) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (isSelected) Color(0xFFDDE3FD) else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .padding(6.dp)
                                ) {
                                    androidx.compose.material3.Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = if (isSelected) Color(0xFF3F51B5) else Color.Black
                                    )
                                }
                            } else {
                                androidx.compose.material3.Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }
    ) { innerPadding ->
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
            ) {
                Text(
                    text = "Đã lưu",
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
                // Hình + trái tim
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
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Heart",
                            tint = Color.Magenta
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TIÊU ĐỀ PHỤ
                Text(
                    text = "Lưu chỗ nghỉ bạn yêu thích",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Tạo danh sách các chỗ nghỉ yêu thích để chia sẻ,\nSo sánh và đặt phòng",
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