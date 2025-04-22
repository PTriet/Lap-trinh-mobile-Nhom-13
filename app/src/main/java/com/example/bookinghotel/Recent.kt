package com.example.bookinghotel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RecentScreen(navController: NavController, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {
        // Tiêu đề
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

        // Nội dung màn hình
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hình ảnh + đồng hồ
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

            // Tiêu đề phụ
            Text(
                text = "Địa điểm bạn đã tới",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Chưa có địa điểm đã tới",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nút "Bắt đầu tìm kiếm"
            Button(
                onClick = {
                    navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0099FF)),
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .align(Alignment.CenterHorizontally)
                        .height(48.dp)
            ) {
                    Text("Bắt đầu tìm kiếm", color = Color.White, fontWeight = FontWeight.Bold)
                }

            Spacer(modifier = Modifier.height(12.dp))

            // Nút "Tạo một danh sách"
            TextButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Tạo một danh sách", color = Color.Blue)
            }
        }
    }
}