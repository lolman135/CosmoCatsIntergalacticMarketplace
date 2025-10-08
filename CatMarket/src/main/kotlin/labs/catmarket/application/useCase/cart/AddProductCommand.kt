package labs.catmarket.application.useCase.cart

import java.util.UUID

data class AddProductCommand(
    val userId: UUID,
    val productId: UUID,
    val quantity: Int
)
