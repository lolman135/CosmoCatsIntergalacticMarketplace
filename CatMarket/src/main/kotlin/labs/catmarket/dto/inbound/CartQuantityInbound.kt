package labs.catmarket.dto.inbound

import jakarta.validation.constraints.Min

data class CartQuantityInbound(
    @field:Min(1, message = "quantity must be 1 at least")
    val quantity: Int
)