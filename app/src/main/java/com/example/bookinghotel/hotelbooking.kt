package com.example.bookinghotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class HotelBooking : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelBookingScreen()
        }
    }
}

@Composable
fun HotelBookingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Thành phần chính cuộn dọc
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(8.dp))
            TitleSection()
            Spacer(modifier = Modifier.height(8.dp))
            SearchBarSection()
            Spacer(modifier = Modifier.height(8.dp))
            OptionsSection()
            Spacer(modifier = Modifier.height(8.dp))
            ImagesSection()
            Spacer(modifier = Modifier.height(8.dp))
            HotelsNearbyScreen()
        }

        // Thanh điều hướng ở cuối
        BottomNavigationBar()
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dinhvi),
                contentDescription = "Location Icon",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "123 Pasteur",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_muiten),
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_chuong),
            contentDescription = "Notification Icon",
            modifier = Modifier.size(36.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun TitleSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "Discover your",
            fontSize = 18.sp,
            color = Color.Gray
        )
        Text(
            text = "perfect place to stay",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun SearchBarSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(12.dp), // Khoảng cách toàn bộ
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon kính lúp lớn hơn
        Icon(
            painter = painterResource(id = R.drawable.ic_kinhlup),
            contentDescription = "Search Icon",
            modifier = Modifier.size(32.dp), // Kích thước được tăng lên
            tint = Color.Unspecified // Giữ nguyên màu gốc
        )
        Spacer(modifier = Modifier.width(12.dp)) // Tăng khoảng cách cho sự cân bằng

        // Ô nhập tìm kiếm
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = "Search hotel",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            },
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(12.dp)) // Tăng khoảng cách cho sự cân bằng

        // Icon bộ lọc lớn hơn
        Icon(
            painter = painterResource(id = R.drawable.ic_boloc),
            contentDescription = "Filter Icon",
            modifier = Modifier.size(32.dp), // Kích thước được tăng lên
            tint = Color.Unspecified // Giữ nguyên màu gốc
        )
    }
}

@Composable
fun OptionsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val options = listOf("Hotel", "Apartments", "Condo", "Mansion")
        options.forEachIndexed { index, option ->
            Text(
                text = option,
                color = if (index == 0) Color.White else Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = if (index == 0) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .background(if (index == 0) Color.Blue else Color.Transparent)
                    .padding(6.dp)
            )
        }
    }
}

@Composable
fun ImagesSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_khacsan1),
            contentDescription = "Hotel Image 1",
            modifier = Modifier
                .weight(1f)
                .height(120.dp)
                .padding(end = 6.dp),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.khacsan2),
            contentDescription = "Hotel Image 2",
            modifier = Modifier
                .weight(1f)
                .height(120.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun HotelsNearbyScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Hotels Nearby",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Show all",
                fontSize = 14.sp,
                color = Color.Blue
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            HotelItem(
                name = "Shangrilla Hotel",
                address = "123 Pasteur",
                price = "$300.00/night",
                rating = "4.9",
                imageResId = R.drawable.img_khacsan1
            )
            Spacer(modifier = Modifier.height(8.dp))

            HotelItem(
                name = "Grand Hotel",
                address = "456 Nguyễn Huệ",
                price = "$250.00/night",
                rating = "4.8",
                imageResId = R.drawable.img_khacsan1
            )
            Spacer(modifier = Modifier.height(8.dp))

            HotelItem(
                name = "Luxury Stay",
                address = "789 Lê Lợi",
                price = "$500.00/night",
                rating = "5.0",
                imageResId = R.drawable.img_khacsan1
            )
        }
    }
}

@Composable
fun HotelItem(
    name: String,
    address: String,
    price: String,
    rating: String,
    imageResId: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hình ảnh khách sạn
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Hotel Image",
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Tên và địa chỉ bên trái
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = address,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Giá và đánh giá bên phải
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = price,
                fontSize = 14.sp,
                color = Color.Red
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_ngoisao), // Sử dụng `Image` thay cho `Icon`
                    contentDescription = "Star Icon",
                    modifier = Modifier.size(16.dp),
                    contentScale = ContentScale.Fit // Hiển thị hình ảnh gốc của ngôi sao
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = rating,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val items = listOf(
            NavigationItem(icon = R.drawable.ic_ngoinha, label = "Home"),
            NavigationItem(icon = R.drawable.ic_dongho, label = "Clock"),
            NavigationItem(icon = R.drawable.ic_traitim, label = "Favorites"),
            NavigationItem(icon = R.drawable.ic_nguoidung, label = "Profile")
        )

        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.label,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified // Không thay đổi màu biểu tượng
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.label,
                    fontSize = 12.sp,
                    color = Color.Black // Text hiển thị màu đen
                )
            }
        }
    }
}

// Lớp dữ liệu cho từng mục điều hướng
data class NavigationItem(
    val icon: Int,
    val label: String
)


@Composable
fun HotelBookingScreen(modifier: Modifier = Modifier) {
    Text(text = "Welcome to Hotel Booking", modifier = modifier)
}