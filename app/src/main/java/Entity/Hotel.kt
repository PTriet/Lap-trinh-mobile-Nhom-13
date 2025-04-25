data class Hotel(
    val id: String = "",
    val name: String = "",
    val location: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val imageUrl: String = "",
    val rooms: List<String> = emptyList() // optional: list of room IDs
)
