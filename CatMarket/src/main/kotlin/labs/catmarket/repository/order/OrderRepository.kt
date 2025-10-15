package labs.catmarket.repository.order

import labs.catmarket.domain.order.Order
import labs.catmarket.repository.BaseRepository
import java.util.UUID

interface OrderRepository : BaseRepository<UUID, Order> {
}