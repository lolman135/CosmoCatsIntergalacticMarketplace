// Kotlin
package labs.catmarket.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class OrderTest {

    private fun item(price: Int, qty: Int, name: String = "Item") =
        Order.OrderItem(UUID.randomUUID(), qty, price, name)

    @Test
    fun shouldCalculateTotalCostForMultipleItems() {
        val order = Order(
            id = null,
            orderItems = listOf(item(100, 2), item(50, 3))
        )
        assertEquals(350, order.totalCost)
    }

    @Test
    fun shouldHaveZeroTotalCostForEmptyOrder() {
        val order = Order(
            id = null,
            orderItems = emptyList()
        )
        assertEquals(0, order.totalCost)
    }

    @Test
    fun shouldMarkAsPaidFromNewAndKeepOtherFields() {
        val items = listOf(item(10, 1))
        val creation = LocalDateTime.now().minusSeconds(1)
        val order = Order(
            id = UUID.randomUUID(),
            creationTime = creation,
            status = Status.NEW,
            orderItems = items
        )

        val paid = order.markAsPaid()

        assertEquals(Status.PAID, paid.status)
        assertEquals(order.id, paid.id)
        assertEquals(order.creationTime, paid.creationTime)
        assertEquals(order.orderItems, paid.orderItems)
    }

    @Test
    fun shouldThrowWhenMarkAsPaidCalledAndStatusIsNotNew() {
        val alreadyPaid = Order(id = null, status = Status.PAID, orderItems = listOf(item(10, 1)))
        assertThrows(IllegalStateException::class.java) { alreadyPaid.markAsPaid() }

        val canceled = Order(id = null, status = Status.CANCELED, orderItems = listOf(item(10, 1)))
        assertThrows(IllegalStateException::class.java) { canceled.markAsPaid() }
    }

    @Test
    fun shouldMarkAsShippedFromPaid() {
        val order = Order(id = null, status = Status.PAID, orderItems = listOf(item(10, 1)))
        val shipped = order.markAsShipped()
        assertEquals(Status.SHIPPED, shipped.status)
    }

    @Test
    fun shouldThrowWhenMarkAsShippedCalledAndStatusIsNotPaid() {
        val newOrder = Order(id = null, status = Status.NEW, orderItems = listOf(item(10, 1)))
        assertThrows(IllegalStateException::class.java) { newOrder.markAsShipped() }

        val canceledOrder = Order(id = null, status = Status.CANCELED, orderItems = listOf(item(10, 1)))
        assertThrows(IllegalStateException::class.java) { canceledOrder.markAsShipped() }
    }

    @Test
    fun shouldMarkAsCanceledFromNewOrPaid() {
        val newOrder = Order(id = null, status = Status.NEW, orderItems = listOf(item(10, 1)))
        assertEquals(Status.CANCELED, newOrder.markAsCanceled().status)

        val paidOrder = Order(id = null, status = Status.PAID, orderItems = listOf(item(10, 1)))
        assertEquals(Status.CANCELED, paidOrder.markAsCanceled().status)
    }

    @Test
    fun shouldThrowWhenMarkAsCanceledCalledFromShipped() {
        val shipped = Order(id = null, status = Status.SHIPPED, orderItems = listOf(item(10, 1)))
        assertThrows(IllegalStateException::class.java) { shipped.markAsCanceled() }
    }
}
