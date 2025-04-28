package com.example.myapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookinghotel.components.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(navController: NavController, innerPadding: PaddingValues, username: String) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    LaunchedEffect(user) {
        if (user == null) {
            // Nếu chưa login, đá về login screen
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    val displayName = user?.email ?: "Guest"  // Nếu chưa có user thì hiện Guest

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedItem = 3
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HeaderSection(username = displayName)
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Nội dung chính
            ContentSection(navController, username)
        }
    }
}

@Composable
fun HeaderSection(username: String) {
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
            Text(
                text = username.firstOrNull()?.toString()?.uppercase() ?: "P", // Hiển thị chữ cái đầu tiên của username
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chào bạn đến với ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "NGAONGER!",
                fontSize = 30.sp,
                color = Color(0xFF1E88E5),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun ContentSection(navController: NavController, email: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        MenuSection(
            title = "Quản lý tài khoản",
            items = listOf("Thông tin cá nhân" to Icons.Default.Person),
            onClick = { label ->
                if (label == "Thông tin cá nhân") {
                    // Điều hướng đến PersonalInfoScreen và truyền email của người dùng
                    navController.navigate("personal_info_screen?email=$email")
                }
            }
        )

        MenuSection(
            title = "Hoạt động du lịch",
            items = listOf("Đã Lưu" to Icons.Default.FavoriteBorder),
            onClick = { label ->
                navController.navigate("saved") {
                    popUpTo(navController.graph.startDestinationId){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        MenuSection(
            title = "Khám phá",
            items = listOf("Ưu đãi" to Icons.Default.LocalOffer),
            onClick = { label ->
                navController.navigate("offer")
            }
        )

        MenuSection(
            title = "Cài đặt",
            items = listOf("Cài đặt thiết bị" to Icons.Default.Settings),
            onClick = { label ->
                navController.navigate("setting")
            }
        )

        MenuSection(
            title = "Trợ giúp",
            items = listOf(
                "Trung tâm thông tin bảo mật" to Icons.Default.Lock,
                "Liên hệ Dịch vụ Khách hàng" to Icons.Default.Phone
            ),
            onClick = { label ->
                when (label) {
                    "Trung tâm thông tin bảo mật" -> navController.navigate("security")
                    "Liên hệ Dịch vụ Khách hàng" -> navController.navigate("contact")
                }
            }
        )
    }
}

@Composable
fun MenuSection(title: String, items: List<Pair<String, ImageVector>>, onClick: (String) -> Unit) {
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
                .clickable { onClick(label) }
                .padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(8.dp)) // 👉 **Thêm dòng này để mỗi item cách nhau 8dp**
    }
}