package labs.catmarket.dto.outbound

import java.time.LocalDateTime
import java.util.UUID

data class OrderDtoOutbound(
    val id: UUID,
    val creationTime: LocalDateTime,
    val totalCost: Int,
    val items: List<OrderItemDtoOutbound>
)
