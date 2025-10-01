package labs.catmarket.infrastructure.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class OrderDtoResponse(
    val id: UUID,
    val creationTime: LocalDateTime,
    val totalCost: Int,
    val items: List<OrderItemDtoResponse>
)
