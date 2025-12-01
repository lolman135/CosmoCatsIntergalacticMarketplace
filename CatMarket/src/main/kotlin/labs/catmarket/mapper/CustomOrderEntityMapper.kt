package labs.catmarket.mapper

import labs.catmarket.domain.Order
import labs.catmarket.repository.entity.OrderEntity

//I decided to write a custom mapper instead of automapper because it's easy to understand what I did here:)
interface CustomOrderEntityMapper {

    fun toDomainFromEntity(entity: OrderEntity): Order
    fun toEntityFromDomain(domain: Order): OrderEntity
}