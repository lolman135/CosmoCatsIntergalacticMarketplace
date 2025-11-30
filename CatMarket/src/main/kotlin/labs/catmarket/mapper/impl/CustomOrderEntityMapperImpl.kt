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

    override fun toEntityFromDomain(domain: Order) =  OrderEntity(
        businessId = domain.id!!,
        createdAt = domain.creationTime,
        status = domain.status,
        items = domain.ordersItems.map {
            OrdersItemEntity(
                product = productJpaRepository.findById(it.productId).orElseThrow {
                    JpaEntityNotFoundException("Product", it.productId) },
                pricePerUnit = it.pricePerUnit,
                quantity = it.quantity
            )
        }.toMutableList()
    )

}