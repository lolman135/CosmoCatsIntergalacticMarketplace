package labs.catmarket.application.useCase.product

import java.util.UUID

data class UpsertProductCommand(
    val name: String,
    val price: Int,
    val imageUrl: String,
    val categoryId: UUID
)