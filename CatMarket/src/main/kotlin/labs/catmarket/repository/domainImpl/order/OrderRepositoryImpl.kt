package labs.catmarket.repository.domainImpl.order

import labs.catmarket.domain.Order
import labs.catmarket.mapper.CustomOrderEntityMapper
import labs.catmarket.repository.OrderJpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository,
    private val customOrderEntityMapper: CustomOrderEntityMapper
) : OrderRepository {

    override fun save(domain: Order): Order {
        val entity = customOrderEntityMapper.toEntityFromDomain(domain)
        return customOrderEntityMapper.toDomainFromEntity(orderJpaRepository.save(entity))
    }

    override fun findAll() = orderJpaRepository.findAll().map { customOrderEntityMapper.toDomainFromEntity(it) }

    override fun findById(id: UUID): Order? {
        val optionalOrderEntity = orderJpaRepository.findByNaturalId(id)
        return optionalOrderEntity.map { customOrderEntityMapper.toDomainFromEntity(it) }.orElse(null)
    }

    override fun deleteById(id: UUID) {
        orderJpaRepository.deleteByNaturalId(id)
    }

    override fun existsById(id: UUID) = orderJpaRepository.existsByNaturalId(id)

    override fun deleteAll() {
        orderJpaRepository.deleteAll()
    }
}