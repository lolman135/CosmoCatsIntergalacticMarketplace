package labs.catmarket.infrastructure.dto.requet.busines

import java.util.UUID

data class CartDtoRequest(
    val userId: UUID,
    val productId: UUID,
    val quantity: Int
)