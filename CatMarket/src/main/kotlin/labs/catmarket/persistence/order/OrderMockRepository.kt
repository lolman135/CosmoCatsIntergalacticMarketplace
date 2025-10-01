package labs.catmarket.persistence.order

import labs.catmarket.domain.order.Order
import labs.catmarket.domain.order.OrderRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class OrderMockRepository : OrderRepository {

    private val orderHolder = mutableMapOf<UUID, Order>()

    override fun deleteById(id: UUID) {
        orderHolder.remove(id)
    }

    override fun save(domain: Order): Order {
        orderHolder[domain.id!!] = domain
        return domain
    }

    override fun findAll() = orderHolder.values.toList()

    override fun findById(id: UUID) = orderHolder[id]

    override fun existsById(id: UUID) = orderHolder.contains(id)
}