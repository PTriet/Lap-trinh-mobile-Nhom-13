package com.example.bookinghotel

import Entity.User
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Composable
fun SignUpScreen(navController: NavController, innerPadding: PaddingValues) {
    val context = LocalContext.current // Lấy context để hiển thị Toast hoặc điều hướng

    Column(
        modifier = Modifier
            .padding(innerPadding) // Áp dụng khoảng cách từ innerPadding
            .padding(horizontal = 32.dp) // Khoảng cách bổ sung
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Thêm IconButton quay lại
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        // Tiêu đề ứng dụng
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "NGAONGER",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Create an account",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Enter your email and password to sign up for this app",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Trường nhập Email
            var email by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("email@domain.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Trường nhập Mật khẩu
            var password by remember { mutableStateOf("") }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Trường nhập lại Mật khẩu
            var confirmPassword by remember { mutableStateOf("") }
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirm Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Nút đăng ký
            Button(
                onClick = {
                    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
                    when {
                        email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                            Toast.makeText(context, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
                        }
                        !email.matches(emailRegex) -> {
                            Toast.makeText(context, "Invalid email format!", Toast.LENGTH_SHORT).show()
                        }
                        password != confirmPassword -> {
                            Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "Account successfully created!", Toast.LENGTH_SHORT).show()
                            navController.navigate("login") // Điều hướng đến màn hình LoginScreen
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text("Continue", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Hoặc tiếp tục với dịch vụ khác
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text(" or ", color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
                Divider(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Nút đăng nhập với Google
            SignInButton(
                text = "Continue with Google",
                textColor = Color.Black,
                iconPainter = painterResource(id = R.drawable.logo_gg),
                onClick = {
                    Toast.makeText(context, "Signing in with Google...", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Nút đăng nhập với số điện thoại
            SignInButton(
                text = "Continue with Phone Number",
                textColor = Color.Black,
                iconPainter = painterResource(id = R.drawable.phone),
                onClick = {
                    Toast.makeText(context, "Signing in with Phone Number...", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Điều khoản
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "By clicking continue, you agree to our",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Row {
                Text(
                    text = "Terms of Service",
                    fontSize = 12.sp,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/terms"))
                        context.startActivity(intent)
                    }
                )
                Text(" and ", color = Color.Gray, fontSize = 12.sp)
                Text(
                    text = "Privacy Policy",
                    fontSize = 12.sp,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/privacy"))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}
////
@SuppressLint("StaticFieldLeak")
val db = FirebaseFirestore.getInstance()
fun addUser(hotel: User) {
    db.collection("User")
        .add(hotel)
        .addOnSuccessListener { documentRef ->
            Log.d("Firestore", "Hotel added with ID: ${documentRef.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error adding hotel", e)
        }
}
// Hàm hiển thị nút đăng ký với Google và số điện thoại
@Composable
fun SignInButton(text: String, iconPainter: Painter, textColor: Color = Color.Black, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(painter = iconPainter, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = textColor)
    }
}
@Override
fun onClick(){
    val newUser = User(
       id = "1",
        name = "Dong Quan",
        email = "quankietsuat@gmail.com",
        phoneNumber = "0354805454"

    )

}