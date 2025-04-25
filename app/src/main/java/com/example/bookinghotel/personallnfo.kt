package com.example.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Mock dữ liệu người dùng
fun getUserInfoByEmail(email: String): UserInfo {
    return when (email) {
        "thanhdz@gmail.com" -> UserInfo(
            name = "Thanh Dz",
            gender = "Nam",
            birthDate = "15/02/1990",
            email = email,
            phone = "0987654321"
        )
        else -> UserInfo(
            name = "Người dùng",
            gender = "Không xác định",
            birthDate = "N/A",
            email = email,
            phone = "N/A"
        )
    }
}

// Data class chứa thông tin người dùng
data class UserInfo(
    val name: String,
    val gender: String,
    val birthDate: String,
    val email: String,
    val phone: String
)

@Composable
fun PersonalInfoScreen(navController: NavController, contentPadding: PaddingValues, email: String) {
    // Lấy thông tin người dùng từ email
    val userInfo = getUserInfoByEmail(email)

    Scaffold(
        modifier = Modifier.padding(contentPadding) // Đảm bảo sử dụng contentPadding truyền vào
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp), // Thêm padding cố định nếu cần
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Tiêu đề và nút quay lại
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }, // Quay lại màn hình trước
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Thông tin cá nhân",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f) // Đảm bảo Text căn giữa
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Thông tin này sẽ được lưu để hỗ trợ việc đặt phòng nhanh hơn.",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Hiển thị từng trường thông tin từ UserInfo
            InfoField(label = "Họ và Tên", value = userInfo.name)
            InfoField(label = "Giới tính", value = userInfo.gender)
            InfoField(label = "Ngày sinh", value = userInfo.birthDate)
            InfoField(label = "Địa chỉ email", value = userInfo.email)
            InfoField(label = "Số điện thoại", value = userInfo.phone)

            Spacer(modifier = Modifier.height(16.dp))

            // Ghi chú phía dưới
            Text(
                text = "Thông tin này sẽ được đơn vị cung cấp dịch vụ liên hệ nếu cần thiết.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun InfoField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}