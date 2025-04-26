package com.example.bookinghotel

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Tạo lớp dữ liệu Hotel để lưu thông tin khách sạn
data class Hotel(
    val name: String,
    val address: String,
    val price: String
)

@Composable
fun HotelBookingScreen(navController: NavController, paddingValues: PaddingValues) {
    val searchText = remember { mutableStateOf("") }
    val hotels = remember {
        listOf(
            Hotel(name = "Shangrilla Hotel", address = "123 Pasteur", price = "$300.00/night"),
            Hotel(name = "Grand Hotel", address = "456 Nguyễn Huệ", price = "$250.00/night"),
            Hotel(name = "Luxury Stay", address = "789 Lê Lợi", price = "$500.00/night")
        )
    }

    val searchQuery = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(8.dp))
        TitleSection()
        Spacer(modifier = Modifier.height(8.dp))
        SearchBarSection(searchText)
        Spacer(modifier = Modifier.height(8.dp))
        OptionsSection()
        Spacer(modifier = Modifier.height(8.dp))
        ImagesSection()
        Spacer(modifier = Modifier.height(8.dp))
        HotelsNearbyScreen(hotels, searchText.value, navController) // Truyền danh sách khách sạn vào đây
    }
}

@Composable
fun HeaderSection() {
    val showDialog = remember { mutableStateOf(false) }
    val addressText = remember { mutableStateOf("123 Pasteur") }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("OK")
                }
            },
            title = { Text("Edit Address") },
            text = {
                TextField(
                    value = addressText.value,
                    onValueChange = { addressText.value = it },
                    placeholder = { Text("Enter new address") }
                )
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable { showDialog.value = true }, // Mở dialog khi nhấn
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dinhvi),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = addressText.value, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_muiten),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
        }
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notification",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun TitleSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Text(text = "Discover your", fontSize = 18.sp, color = Color.Gray)
        Text(text = "perfect place to stay", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SearchBarSection(searchText: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_kinhnup), contentDescription = null, modifier = Modifier.size(32.dp), tint = Color.Unspecified)
        Spacer(modifier = Modifier.width(12.dp))
        TextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            placeholder = { Text("Search hotel", fontSize = 14.sp, color = Color.Gray) },
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(painter = painterResource(id = R.drawable.ic_boloc), contentDescription = null, modifier = Modifier.size(32.dp), tint = Color.Unspecified)
    }
}

@Composable
fun OptionsSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
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
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_khacsan1),
            contentDescription = null,
            modifier = Modifier.weight(1f).height(120.dp).padding(end = 6.dp),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.khacsan2),
            contentDescription = null,
            modifier = Modifier.weight(1f).height(120.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun HotelsNearbyScreen(hotels: List<Hotel>, searchQuery: String, navController: NavController) {
    val filteredHotels = hotels.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Hotels Nearby", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "Show all", fontSize = 14.sp, color = Color.Blue)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredHotels.isEmpty()) {
            Text("No results found", color = Color.Gray)
        } else {
            Column {
                filteredHotels.forEach { hotel ->
                    HotelItem(
                        name = hotel.name,
                        address = hotel.address,
                        price = hotel.price,
                        rating = "5.0",
                        imageResId = R.drawable.img_khacsan1,
                        onClick = { name, address, price, rating ->
                            // Truyền tham số vào URL khi điều hướng
                            val encodedName = Uri.encode(name)
                            val encodedAddress = Uri.encode(address)
                            val encodedPrice = Uri.encode(price)
                            val encodedRating = Uri.encode(rating)

                            navController.navigate("inforhotel/$encodedName/$encodedAddress/$encodedPrice/$encodedRating")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun HotelItem(
    name: String,
    address: String,
    price: String,
    rating: String,
    imageResId: Int,
    onClick: (String, String, String, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
            .clickable { onClick(name, address, price, rating) } // Truyền dữ liệu khi nhấn vào
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = address, fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(text = price, fontSize = 14.sp, color = Color.Red)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_ngoisao),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = rating, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


