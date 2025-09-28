package labs.catmarket.infrastructure.dto.response

import java.util.UUID

data class CartDtoResponse(
    val userId: UUID,
    val items: List<CartItemDtoResponse>
)