package com.example.bookinghotel


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun HotelBookingScreen( modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.hotel1),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(3f/4f).height(150.dp),
            contentScale = ContentScale.Crop
        )
        Card(
            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp), // Bo góc trên
            backgroundColor = Color.White,
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter).fillMaxHeight(3f/5f)// Đặt lên trên ảnh
        ) {
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Swiss hotel",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "211B Baker Street, London, England",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    repeat(5) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color.Yellow)
                    }
                    Text(text = "5.0 ",  fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 5.dp, top = 5.dp))
                    Text(text = " - 1231 Reviews", fontWeight = FontWeight.W300, modifier = Modifier.padding(start = 2.dp, top = 5.dp))
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(text = "Description", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Hotel Room means an area that is designed and constructed to be occupied by one or more persons on Hotel Property, which is separate",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )

                Text(
                    text = "Read more",
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth() // Hàng trải dài toàn chiều ngang
                ) {
                    repeat(4) { index ->
                        Image(
                            painter = painterResource(R.drawable.hotel2),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f) // Đảm bảo mỗi ảnh chiếm một phần bằng nhau trong hàng
                                .aspectRatio(1f) // Duy trì tỷ lệ 1:1
                                .padding(end = if (index < 3) 8.dp else 0.dp), // Chỉ ảnh cuối không có padding
                            contentScale = ContentScale.Crop // Cắt ảnh theo khung để phù hợp
                        )
                    }
                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp) // Di chuyển ô vào bên trong
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = Color(0xFFFFFFFF)
                        , shape = CircleShape)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    modifier = Modifier.size(22.dp)
                )
            }
            }

            Box(modifier = Modifier.fillMaxSize()) {
            // Phần chứa bookmark và button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 50.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Biểu tượng bookmark
                Box(
                    modifier = Modifier
                        .size(60.dp) // Kích thước vòng tròn
                        .background(color = Color.LightGray, shape = CircleShape), // Vòng tròn màu xám nhạt
                    contentAlignment = Alignment.Center // Căn giữa biểu tượng bên trong
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Bookmark Icon",
                        modifier = Modifier.size(24.dp) // Kích thước biểu tượng
                    )
                }


                // Nút "Book Now"
                Button(
                    onClick = { /* Thêm logic cho button */ },
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomEnd = 50.dp, bottomStart = 50.dp),
                    modifier = Modifier
                        .align(Alignment.CenterVertically).width(220.dp).height(55.dp)
                ) {
                    Text(text = "Book Now")
                }
            }
        }


    }
}
