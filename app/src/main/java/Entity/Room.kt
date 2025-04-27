data class Room(
    val id: String = "",
    val hotelId: String = "",
    val name: String = "",
    val price: Int = 0,
    val type: String = "",  // e.g., "Deluxe", "Standard"
    val maxGuests: Int = 0,
    val isAvailable: Boolean = true,
    val imageUrl: String = ""
)
