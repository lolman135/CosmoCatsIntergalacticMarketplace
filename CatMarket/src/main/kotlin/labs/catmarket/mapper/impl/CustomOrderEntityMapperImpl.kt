package labs.catmarket.mapper.impl

import labs.catmarket.domain.Order
import labs.catmarket.mapper.CustomOrderEntityMapper
import labs.catmarket.repository.ProductJpaRepository
import labs.catmarket.repository.entity.OrderEntity
import labs.catmarket.repository.entity.OrdersItemEntity
import labs.catmarket.repository.exception.JpaEntityNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomOrderEntityMapperImpl(private val productJpaRepository: ProductJpaRepository) : CustomOrderEntityMapper {

    override fun toDomainFromEntity(entity: OrderEntity) =  Order(
        id = entity.businessId,
        creationTime = entity.createdAt,
        status = entity.status,
        ordersItems = entity.items?.map {
            Order.OrdersItem(
                productId = it.product.businessId,
                quantity = it.quantity,
                pricePerUnit = it.pricePerUnit
            )
        } ?: emptyList()
    )

    override fun toEntityFromDomain(domain: Order): OrderEntity {
        val orderEntity = OrderEntity(
            businessId = domain.id!!,
            createdAt = domain.creationTime,
            status = domain.status,
            items = mutableListOf()
        )

        val items = domain.ordersItems.map { item ->
            OrdersItemEntity(
                product = productJpaRepository.findByNaturalId(item.productId).orElseThrow {
                    JpaEntityNotFoundException("Product", item.productId)
                },
                pricePerUnit = item.pricePerUnit,
                quantity = item.quantity,
                order = orderEntity
            )
        }.toMutableList()

        orderEntity.items = items

        return orderEntity
    }

}