package com.example.bookinghotel

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.datetime.*

@Composable
fun BookingFormScreen(
    navController: NavController,
    nameHotel: String,
    addressHotel: String,
    priceHotel: String,
    ratingHotel: String,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var checkInDate by remember { mutableStateOf(TextFieldValue("")) }
    var checkOutDate by remember { mutableStateOf(TextFieldValue("")) }
    var voucherCode by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var dateOrderError by remember { mutableStateOf(false) }
    var discountAmount by remember { mutableStateOf(0.0) }
    var isVoucherValid by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Booking Form",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Price: $priceHotel",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                phoneError = null
            },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = phoneError != null,
            supportingText = {
                phoneError?.let { errorMsg ->
                    Text(errorMsg, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        DateInputField("Check-in Date", checkInDate, { checkInDate = it }, isDateOrderInvalid = dateOrderError)
        Spacer(modifier = Modifier.height(8.dp))
        DateInputField("Check-out Date", checkOutDate, { checkOutDate = it }, isDateOrderInvalid = dateOrderError)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = voucherCode,
            onValueChange = { voucherCode = it },
            label = { Text("Voucher Code (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (voucherCode.equals("DISCOUNT10", ignoreCase = true)) {
            isVoucherValid = true
            discountAmount = 10.0
            Text(
                text = "Discount Applied: 10%",
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            isVoucherValid = false
            discountAmount = 0.0
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (phone.isEmpty() || !phone.all { it.isDigit() }) {
                    phoneError = "Chỉ được chứa số"
                    return@Button
                }
                if (phone.length != 10) {
                    phoneError = "Số điện thoại phải đúng 10 số"
                    return@Button
                }
                if (!phone.startsWith("0")) {
                    phoneError = "Số đầu tiên phải là số 0"
                    return@Button
                }

                val isValidFormat = isValidDateFormat(checkInDate.text) && isValidDateFormat(checkOutDate.text)
                val checkIn = parseDate(checkInDate.text)
                val checkOut = parseDate(checkOutDate.text)
                val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

                if (!isValidFormat || checkIn == null || checkOut == null || checkIn < today || checkOut <= checkIn) {
                    dateOrderError = true
                    return@Button
                }

                val user = auth.currentUser
                if (user != null) {
                    val bookingData = hashMapOf(
                        "name" to nameHotel,
                        "address" to addressHotel,
                        "price" to priceHotel,
                        "rating" to ratingHotel,
                        "fullName" to fullName,
                        "phone" to phone,
                        "checkInDate" to checkInDate.text,
                        "checkOutDate" to checkOutDate.text,
                        "discount" to if (isVoucherValid) "10%" else "0%"
                    )

                    db.collection("users")
                        .document(user.uid)
                        .collection("recentBookings")
                        .add(bookingData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Đặt phòng thành công!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Lỗi khi lưu đặt phòng!", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "Bạn cần đăng nhập để đặt phòng!", Toast.LENGTH_SHORT).show()
                    navController.navigate("login?pendingSave=true")
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
    val formatted = remember(value.text) { formatDateInput(value.text) }
    val isValid = remember(formatted) { isValidDateFormat(formatted) }

    OutlinedTextField(
        value = TextFieldValue(formatted, TextRange(formatted.length)),
        onValueChange = {
            val digits = it.text.filter { ch -> ch.isDigit() }
            val formattedText = formatDateInput(digits)
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
                isDateOrderInvalid -> Text("Ngày không hợp lệ", color = MaterialTheme.colorScheme.error)
            }
        }
    )
}

fun formatDateInput(input: String): String {
    val digits = input.filter { it.isDigit() }.take(8)
    val builder = StringBuilder()
    for (i in digits.indices) {
        builder.append(digits[i])
        if ((i == 1 || i == 3) && i != digits.lastIndex) builder.append('/')
    }
    return builder.toString()
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
