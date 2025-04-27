package com.example.bookinghotel

import Entity.User
import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginScreen(navController: NavController, innerPadding: PaddingValues) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Tiêu đề và thông tin hướng dẫn
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            Text("NGAONGER", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
            Spacer(modifier = Modifier.height(32.dp))
            Text("Log in to your account", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Enter your email and password to log in", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            // Trường nhập Email
            InputField(
                value = email,
                onValueChange = { email = it },
                placeholder = "email@domain.com",
                keyboardType = KeyboardType.Email,
                isError = email.isNotEmpty() && !Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$").matches(email) // Kiểm tra lỗi định dạng email
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Trường nhập Mật khẩu
            InputField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Nút đăng nhập
            Button(
                onClick = {
                    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
                    when {
                        email.isBlank() || password.isBlank() -> {
                            showToast(context, "Fields cannot be empty!")
                        }
                        !email.matches(emailRegex) -> {
                            showToast(context, "Invalid email format!")
                        }
                        else -> {
                            showToast(context, "Logging in...")
                            navController.navigate("information?username=${Uri.encode(email)}") // Điều hướng và truyền email làm username
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text("Log in", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            OrDivider()
            Spacer(modifier = Modifier.height(16.dp))
            SignInButton("Log in with Google", painterResource(id = R.drawable.logo_gg)) { showToast(context, "Logging in with Google...") }
            Spacer(modifier = Modifier.height(8.dp))
            SignInButton("Log in with Phone Number", painterResource(id = R.drawable.phone)) { showToast(context, "Logging in with Phone Number...") }
        }
    }
}


@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        isError = isError
    )
}

@Composable
fun OrDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(modifier = Modifier.weight(1f))
        Text(" or ", color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
        Divider(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SignInButton(text: String, iconPainter: Painter, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(48.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(painter = iconPainter, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.Black)
    }
}

fun showToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}