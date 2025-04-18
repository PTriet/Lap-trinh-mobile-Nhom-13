package com.example.bookinghotel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*

@Composable
fun SignUpScreen(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        //Phần đăng nhập
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
                text = "Enter your email to sign up for this app",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            var email by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                placeholder = { Text("email@domain.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                )
            ){
                Text("Continue", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Divider(modifier = Modifier.weight(1f))
                Text(" or ", color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
                Divider(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))

            SignInButton(
                text = "Continue with Google",
                textColor = Color.Black,
                iconPainter = painterResource(id = R.drawable.logo_gg),
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))

            SignInButton(
                text = "Continue with Phone Number",
                textColor = Color.Black,
                iconPainter = painterResource(id = R.drawable.phone),
                onClick ={}
            )
        }

        //Phần điều khoản
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
             Text(
                 text = "By clicking continue, you agree to our",
                 fontSize = 12.sp,
                 color = Color.Gray
             )
            Row{
                Text(
                    text = "Terms of Service",
                    fontSize = 12.sp,
                    color = Color.Blue,
                    modifier = Modifier.clickable {  }
                )
                Text(" and ", color = Color.Gray, fontSize = 12.sp)
                Text(
                    text = "Privacy Policy",
                    fontSize = 12.sp,
                    color = Color.Blue,
                    modifier = Modifier.clickable {  }
                )
            }
        }
    }
}

@Composable
fun SignInButton(
    text : String,
    iconPainter: Painter,
    textColor : Color = Color.Black,
    onClick: () -> Unit
){
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(text, color = textColor)
    }
}