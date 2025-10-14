package labs.catmarket.dto.response

data class CartItemDtoResponse(
    val product: String,
    val price: Int,
    val quantity: Int
)