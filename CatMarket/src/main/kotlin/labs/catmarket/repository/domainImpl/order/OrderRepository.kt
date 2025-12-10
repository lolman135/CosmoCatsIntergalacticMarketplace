package labs.catmarket.repository.domainImpl.order

import labs.catmarket.domain.Order
import labs.catmarket.repository.domainImpl.BaseRepository
import java.util.UUID

interface OrderRepository : BaseRepository<UUID, Order> {
}