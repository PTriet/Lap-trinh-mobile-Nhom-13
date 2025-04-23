package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bookinghotel.components.BottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceSettingsScreen(navController: NavController, paddingValues: PaddingValues) {
    val selectedItem = remember { mutableStateOf(3) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Cài đặt thiết bị",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Điều hướng quay lại
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