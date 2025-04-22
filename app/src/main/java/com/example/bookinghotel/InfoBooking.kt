package com.example.bookinghotel

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

@Composable
fun BookingFormScreen(
    modifier: Modifier = Modifier,
    onBookingConfirmed: (String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var checkInDate by remember { mutableStateOf(TextFieldValue("")) }
    var checkOutDate by remember { mutableStateOf(TextFieldValue("")) }
    var dateOrderError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Booking Details", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            DateInputField(
                label = "Check-in Date",
                value = checkInDate,
                onValueChange = {
                    checkInDate = it
                    dateOrderError = false
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            DateInputField(
                label = "Check-out Date",
                value = checkOutDate,
                onValueChange = {
                    checkOutDate = it
                    dateOrderError = false
                },
                isDateOrderInvalid = dateOrderError
            )
        }

        Button(
            onClick = {
                val isValidFormat = isValidDateFormat(checkInDate.text) && isValidDateFormat(checkOutDate.text)
                val checkIn = parseDate(checkInDate.text)
                val checkOut = parseDate(checkOutDate.text)

                if (isValidFormat && checkIn != null && checkOut != null) {
                    if (!(checkOut > checkIn)) {
                        dateOrderError = true
                    } else {
                        onBookingConfirmed(name, phone, checkInDate.text, checkOutDate.text)
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