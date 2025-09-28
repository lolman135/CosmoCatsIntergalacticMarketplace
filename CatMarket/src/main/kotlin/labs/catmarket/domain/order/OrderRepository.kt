package labs.catmarket.domain.order

import labs.catmarket.domain.baseRepository.BaseRepository
import java.util.UUID

interface OrderRepository : BaseRepository<UUID, Order> {
}