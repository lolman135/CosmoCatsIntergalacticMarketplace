package labs.catmarket.infrastructure.mapper

import labs.catmarket.domain.order.Order
import labs.catmarket.infrastructure.dto.response.OrderDtoResponse
import labs.catmarket.infrastructure.dto.response.OrderItemDtoResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface OrderMapper {

    @Mapping(source = "orderItems", target = "items")
    fun toDto(order: Order): OrderDtoResponse

    fun toDtoItems(orderItems: List<Order.OrderItem>): List<OrderItemDtoResponse>

    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "pricePerUnit", target = "productPrice")
    @Mapping(source = "quantity", target = "quantity")
    fun toDtoItem(orderItem: Order.OrderItem): OrderItemDtoResponse
}