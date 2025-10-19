package labs.catmarket.domain

import java.time.LocalDateTime
import java.util.UUID

data class Order(
    val id: UUID?,
    val creationTime: LocalDateTime = LocalDateTime.now(),
    val status: Status = Status.NEW,
    val orderItems: List<OrderItem>
) {
    val totalCost: Int
        get() = orderItems.sumOf { it.pricePerUnit * it.quantity }

    data class OrderItem(
        val productId: UUID,
        val quantity: Int,
        val pricePerUnit: Int,
        val productName: String
    )

    fun markAsPaid(): Order {
        if (status != Status.NEW) throw IllegalStateException("Order cannot be paid")
        return copy(status = Status.PAID)
    }

    fun markAsShipped(): Order {
        if (status != Status.PAID) throw IllegalStateException("Order must be paid before shipping")
        return copy(status = Status.SHIPPED)
    }

    fun markAsCanceled(): Order {
        if (status == Status.SHIPPED) throw IllegalStateException("Cannot cancel shipped order")
        return copy(status = Status.CANCELED)
    }
}