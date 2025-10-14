package labs.catmarket.dto.requet.busines

import jakarta.validation.constraints.Min

data class CartQuantityRequest(
    @field:Min(1, message = "quantity must be 1 at least")
    val quantity: Int
)