package labs.catmarket.dto.outbound

import java.util.UUID

data class ProductDtoOutbound(
    val id: UUID,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val category: String
)