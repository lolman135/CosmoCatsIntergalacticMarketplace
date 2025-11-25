package labs.catmarket.repository.domainrepository.order

import labs.catmarket.domain.Order
import labs.catmarket.repository.BaseRepository
import java.util.UUID

interface OrderRepository : BaseRepository<UUID, Order> {
}