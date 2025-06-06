package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bookinghotel.UserPreferences
import com.example.bookinghotel.components.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceSettingsScreen(navController: NavController, paddingValues: PaddingValues) {
    val selectedItem = remember { mutableStateOf(3) }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    val shouldLogout = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Cài đặt thiết bị", fontWeight = FontWeight.Bold, fontSize = 24.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFE0E0E0),
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, selectedItem = selectedItem.value)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
        ) {
            SettingField("Ngôn ngữ", "Tiếng Việt")
            SettingField("Tiền tệ", "VNĐ")
            SettingField("Đơn vị", "Hệ mét (km, m²)")
            SettingField("Thông báo", "Bật")

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    auth.signOut()
                    shouldLogout.value = true
                }
            ) {
                Text("Đăng xuất")
            }
        }
    }

    // Nghe sự kiện Logout để xử lý ngoài Button
    LaunchedEffect(shouldLogout.value) {
        if (shouldLogout.value) {
            UserPreferences.setLoggedIn(context, false)
            navController.navigate("prioritize") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun SettingField(title: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = value, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceSettingsScreenPreview() {
    DeviceSettingsScreen(navController = rememberNavController(), paddingValues = PaddingValues(0.dp))
}