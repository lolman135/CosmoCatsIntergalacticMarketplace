package labs.catmarket.repository

import labs.catmarket.repository.entity.OrderEntity
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderJpaRepository : NaturalIdRepository<OrderEntity, UUID> {
}