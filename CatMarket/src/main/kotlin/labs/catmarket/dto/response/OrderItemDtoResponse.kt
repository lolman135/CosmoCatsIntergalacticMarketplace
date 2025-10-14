package labs.catmarket.dto.response

data class OrderItemDtoResponse(
    val productName: String,
    val productPrice: Int,
    val quantity: Int
)