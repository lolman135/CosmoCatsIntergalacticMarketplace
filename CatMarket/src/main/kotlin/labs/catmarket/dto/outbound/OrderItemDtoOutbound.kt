package labs.catmarket.dto.outbound

data class OrderItemDtoOutbound(
    val productName: String,
    val productPrice: Int,
    val quantity: Int
)