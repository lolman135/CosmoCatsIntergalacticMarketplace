package labs.catmarket.infrastructure.dto.response.busines

import java.util.UUID

data class ProductDtoResponse(
    val id: UUID,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val category: String
)