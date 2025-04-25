package com.example.myapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UserMenuScreen(navController: NavController, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {
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
                text = "Đăng nhập để quản lý chuyến đi và nhận phiếu giảm giá ",
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                navController.navigate("signup")
            }) {
                Text("Đăng Nhập", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            MenuItem(icon = Icons.Default.Lock, text = "Trung tâm thông tin bảo mật") {
                navController.navigate("security")
            }
            MenuItem(icon = Icons.Default.FavoriteBorder, text = "Đã Lưu") {
                navController.navigate("saved"){
                    popUpTo(navController.graph.startDestinationId){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            MenuItem(icon = Icons.Default.Phone, text = "Liên hệ Dịch vụ Khách hàng") {
                navController.navigate("contact")
            }
            MenuItem(icon = Icons.Default.LocalOffer, text = "Ưu đãi") {
                navController.navigate("offer")
            }
            MenuItem(icon = Icons.Default.Settings, text = "Cài đặt") {
                navController.navigate("setting")
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
            .padding(vertical = 16.dp)
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