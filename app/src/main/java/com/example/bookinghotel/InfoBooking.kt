package com.example.bookinghotel

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import kotlinx.datetime.*

@Composable
fun BookingFormScreen(
    modifier: Modifier = Modifier,
    onBookingConfirmed: (String, String, String, String, Double) -> Unit,  // Thêm tham số giá phòng
    onBackPressed: () -> Unit // Thêm tham số callback quay lại
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var checkInDate by remember { mutableStateOf(TextFieldValue("")) }
    var checkOutDate by remember { mutableStateOf(TextFieldValue("")) }
    var dateOrderError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    // Thêm giá phòng và mã voucher
    var roomPrice by remember { mutableStateOf(100.0) }  // Giá phòng mặc định là 100 USD
    var voucherCode by remember { mutableStateOf("") }
    var discountAmount by remember { mutableStateOf(0.0) }  // Giảm giá voucher
    var isVoucherValid by remember { mutableStateOf(false) }
    var voucherError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top // Đảm bảo các phần tử nằm từ trên xuống
    ) {
        // Sử dụng Box để căn giữa tiêu đề và giữ nút quay lại ở bên trái
        Box(modifier = Modifier.fillMaxWidth()) {
            // Nút quay lại
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .align(Alignment.CenterStart)  // Đặt nút quay lại ở đầu
                    .padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            // Tiêu đề "Order" căn giữa trong Box
            Text(
                text = "Order",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)  // Căn giữa tiêu đề
                    .padding(start = 16.dp), // Thêm padding nếu cần
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Các trường nhập liệu còn lại...
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ô nhập số điện thoại
        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                phoneError = false
            },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            isError = phoneError,
            supportingText = {
                if (phoneError) {
                    Text("Chỉ được chứa số", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ô nhập ngày check-in
        DateInputField(
            label = "Check-in Date",
            value = checkInDate,
            onValueChange = {
                checkInDate = it
                dateOrderError = false
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ô nhập ngày check-out
        DateInputField(
            label = "Check-out Date",
            value = checkOutDate,
            onValueChange = {
                checkOutDate = it
                dateOrderError = false
            },
            isDateOrderInvalid = dateOrderError
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ô nhập mã voucher (có thể bỏ trống)
        OutlinedTextField(
            value = voucherCode,
            onValueChange = {
                voucherCode = it
                voucherError = false
            },
            label = { Text("Voucher Code (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            isError = voucherError,
            supportingText = {
                if (voucherError) {
                    Text("Mã voucher không hợp lệ", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Hiển thị giá phòng trước khi áp dụng mã voucher
        Text("Room Price: \$${"%.2f".format(roomPrice)}")

        // Kiểm tra mã voucher và áp dụng giảm giá nếu có
        if (voucherCode.isNotEmpty()) {
            // Kiểm tra mã voucher (Ví dụ: "DISCOUNT10")
            if (voucherCode == "DISCOUNT10") {
                isVoucherValid = true
                discountAmount = 10.0 // Giảm giá 10% nếu voucher hợp lệ
            } else {
                isVoucherValid = false
                voucherError = true
                discountAmount = 0.0
            }
        } else {
            isVoucherValid = false
            discountAmount = 0.0
        }

        // Hiển thị số tiền giảm nếu có voucher
        if (isVoucherValid) {
            Text("Discount Applied: \$${"%.2f".format(roomPrice * (discountAmount / 100))}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút xác nhận đặt phòng
        Button(
            onClick = {
                // Kiểm tra số điện thoại có hợp lệ không
                if (phone.all { it.isDigit() }) {
                    phoneError = false
                } else {
                    phoneError = true
                    return@Button
                }

                val isValidFormat = isValidDateFormat(checkInDate.text) && isValidDateFormat(checkOutDate.text)
                val checkIn = parseDate(checkInDate.text)
                val checkOut = parseDate(checkOutDate.text)

                if (isValidFormat && checkIn != null && checkOut != null) {
                    if (!(checkOut > checkIn)) {
                        dateOrderError = true
                    } else {
                        val finalPrice = roomPrice - (roomPrice * (discountAmount / 100))  // Tính giá sau giảm giá
                        onBookingConfirmed(name, phone, checkInDate.text, checkOutDate.text, finalPrice)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Confirm Booking")
        }
    }
}





@Composable
fun DateInputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isDateOrderInvalid: Boolean = false
) {
    fun format(input: String): String {
        val digits = input.filter { it.isDigit() }.take(8)
        val sb = StringBuilder()
        for (i in digits.indices) {
            sb.append(digits[i])
            if ((i == 1 || i == 3) && i != digits.lastIndex) sb.append('/')
        }
        return sb.toString()
    }

    fun isValidDate(text: String): Boolean {
        val regex = Regex("""\d{2}/\d{2}/\d{4}""")
        if (!regex.matches(text)) return false
        val parts = text.split("/")
        val day = parts[0].toIntOrNull() ?: return false
        val month = parts[1].toIntOrNull() ?: return false
        val year = parts[2].toIntOrNull() ?: return false
        return day in 1..31 && month in 1..12 && year in 1000..9999
    }

    val formatted = format(value.text)
    val newCursor = TextRange(formatted.length)
    val isValid = isValidDate(formatted)

    OutlinedTextField(
        value = TextFieldValue(formatted, newCursor),
        onValueChange = {
            val rawDigits = it.text.filter { ch -> ch.isDigit() }
            val formattedText = format(rawDigits)
            onValueChange(TextFieldValue(formattedText, TextRange(formattedText.length)))
        },
        label = { Text(label) },
        placeholder = { Text("dd/MM/yyyy") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        isError = !isValid || isDateOrderInvalid,
        supportingText = {
            when {
                !isValid -> Text("Ngày không hợp lệ", color = MaterialTheme.colorScheme.error)
                isDateOrderInvalid -> Text("Ngày check-out phải sau check-in", color = MaterialTheme.colorScheme.error)
            }
        }
    )
}

fun isValidDateFormat(text: String): Boolean {
    val regex = Regex("""\d{2}/\d{2}/\d{4}""")
    return regex.matches(text)
}

fun parseDate(input: String): LocalDate? {
    return try {
        val parts = input.split("/")
        val day = parts[0].toInt()
        val month = parts[1].toInt()
        val year = parts[2].toInt()
        LocalDate(year, month, day)
    } catch (e: Exception) {
        null
    }
}
