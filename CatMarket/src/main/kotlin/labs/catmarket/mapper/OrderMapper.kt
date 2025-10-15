package labs.catmarket.mapper

import labs.catmarket.domain.order.Order
import labs.catmarket.dto.outbound.OrderDtoOutbound
import labs.catmarket.dto.outbound.OrderItemDtoOutbound
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface OrderMapper {

    @Mapping(source = "orderItems", target = "items")
    fun toDto(order: Order): OrderDtoOutbound

    fun toDtoItems(orderItems: List<Order.OrderItem>): List<OrderItemDtoOutbound>

    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "pricePerUnit", target = "productPrice")
    @Mapping(source = "quantity", target = "quantity")
    fun toDtoItem(orderItem: Order.OrderItem): OrderItemDtoOutbound
}