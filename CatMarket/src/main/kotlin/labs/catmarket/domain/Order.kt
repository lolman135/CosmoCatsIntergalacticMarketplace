package labs.catmarket.domain

import java.time.LocalDateTime
import java.util.UUID

data class Order(
    val id: UUID?,
    val creationTime: LocalDateTime = LocalDateTime.now(),
    val status: Status = Status.NEW,
    val orderItems: List<OrderItem>
) {

    data class OrderItem(
        val productId: UUID,
        val quantity: Int,
        // Let me explain. I've decided to live this field both in domain model and in db.
        // This is because price of product can change along the lifecycle of this product,
        // but product contains information about purchase at the moment of the purchasing.
        // So order is just a record, contract or that tell us when it was made
        val pricePerUnit: Int,
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