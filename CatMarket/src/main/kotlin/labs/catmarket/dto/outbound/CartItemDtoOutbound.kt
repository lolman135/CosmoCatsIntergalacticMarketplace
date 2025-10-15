package labs.catmarket.dto.outbound

data class CartItemDtoOutbound(
    val product: String,
    val price: Int,
    val quantity: Int
)