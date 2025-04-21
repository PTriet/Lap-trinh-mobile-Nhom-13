package com.example.myapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(modifier: Modifier = Modifier) {
    val selectedItem = remember { mutableStateOf(3) }

    Scaffold(
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
                    val isProfile = index == 3

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedItem.value = index },
                        icon = {
                            if (isProfile) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (isSelected) Color(0xFFDDE3FD) else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .padding(6.dp)
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = if (isSelected) Color(0xFF3F51B5) else Color.Black
                                    )
                                }
                            } else {
                                Icon(
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
        Column(modifier = Modifier.fillMaxSize()) {

            // Header (outside padding)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE0E0E0))
                    .padding(top = 50.dp, bottom = 16.dp),
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

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("P", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text("Chào bạn đến với ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "NGAONGER!",
                        fontSize = 36.sp,
                        color = Color(0xFF1E88E5),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            // Content below with padding
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                MenuSection(
                    title = "Quản lý tài khoản",
                    items = listOf("Thông tin cá nhân" to Icons.Default.Person)
                )

                MenuSection(
                    title = "Hoạt động du lịch",
                    items = listOf("Đã Lưu" to Icons.Default.FavoriteBorder)
                )

                MenuSection(
                    title = "Khám phá",
                    items = listOf("Ưu đãi" to Icons.Default.LocalOffer)
                )

                MenuSection(
                    title = "Cài đặt",
                    items = listOf("Cài đặt thiết bị" to Icons.Default.Settings)
                )

                MenuSection(
                    title = "Trợ giúp",
                    items = listOf(
                        "Trung tâm thông tin bảo mật" to Icons.Default.Lock,
                        "Liên hệ Dịch vụ Khách hàng" to Icons.Default.Phone
                    )
                )
            }
        }
    }
}

@Composable
fun MenuSection(title: String, items: List<Pair<String, ImageVector>>) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    items.forEach { (label, icon) ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(vertical = 12.dp)
        ) {
            Icon(imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = Color.Black)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, fontSize = 16.sp)
        }
    }
}
