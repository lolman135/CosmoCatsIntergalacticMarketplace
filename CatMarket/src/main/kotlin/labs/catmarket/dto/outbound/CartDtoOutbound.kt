package labs.catmarket.dto.outbound

import java.util.UUID

data class CartDtoOutbound(val userId: UUID, val items: List<CartItemDtoOutbound>)