data class Booking(
    val id: String = "",
    val userId: String = "",
    val hotelId: String = "",
    val roomId: String = "",
    val checkIn: String = "",    // YYYY-MM-DD
    val checkOut: String = "",
    val totalPrice: Int = 0,
    val status: String = "pending" // "confirmed", "canceled"
)
