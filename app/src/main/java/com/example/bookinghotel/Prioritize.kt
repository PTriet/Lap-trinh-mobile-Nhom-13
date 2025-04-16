package com.example.myapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMenuScreen(modifier: Modifier = Modifier) {
    val selectedItem = remember { mutableStateOf(3) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedItem.value) { index ->
                selectedItem.value = index
            }
        }
    ) { innerPadding ->

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            // ✨ HEADER nằm ngoài innerPadding -> không bị đẩy xuống
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE0E0E0))
                    .padding(top = 0.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, shape = CircleShape)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Đăng nhập để quản lý chuyến đi và nhận phiếu giảm giá " +
                            "Genius tại các chỗ nghỉ trên toàn cầu.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = { /* TODO: Login */ }) {
                    Text("Đăng Nhập")
                }
            }

            // ✨ BODY nằm trong innerPadding
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                MenuItem(icon = Icons.Default.Lock, text = "Trung tâm thông tin bảo mật")
                MenuItem(icon = Icons.Default.FavoriteBorder, text = "Đã Lưu")
                MenuItem(icon = Icons.Default.Phone, text = "Liên hệ Dịch vụ Khách hàng")
                MenuItem(icon = Icons.Default.LocalOffer, text = "Ưu đãi")
                MenuItem(icon = Icons.Default.Settings, text = "Cài đặt")
            }
        }
    }
}


@Composable
fun MenuItem(icon: ImageVector, text: String, onClick: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 32.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(28.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 20.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun UserMenuScreenPreview() {
    UserMenuScreen()
}