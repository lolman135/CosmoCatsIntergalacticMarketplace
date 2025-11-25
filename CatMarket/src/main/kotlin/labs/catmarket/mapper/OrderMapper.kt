package labs.catmarket.mapper

import labs.catmarket.domain.Order
import labs.catmarket.dto.outbound.OrderDtoOutbound
import labs.catmarket.dto.outbound.OrderItemDtoOutbound
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.context.annotation.Import

@Mapper(componentModel = "spring")
interface OrderMapper {

    @Mapping(source = "orderItems", target = "items")
    fun toDto(order: Order, @Context orderMapperHelper: OrderMapperHelper): OrderDtoOutbound

    fun toDtoItems(
        orderItems: List<Order.OrderItem>,
        @Context
        orderMapperHelper: OrderMapperHelper
    ): List<OrderItemDtoOutbound>

    @Mapping(target = "productName", expression = "java(orderMapperHelper.getProductNameFromId(orderItem.getProductId()))")
    @Mapping(source = "pricePerUnit", target = "productPrice")
    @Mapping(source = "quantity", target = "quantity")
    fun toDtoItem(orderItem: Order.OrderItem, @Context orderMapperHelper: OrderMapperHelper): OrderItemDtoOutbound
}