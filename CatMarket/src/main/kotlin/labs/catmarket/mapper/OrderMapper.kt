package labs.catmarket.mapper

import labs.catmarket.domain.Order
import labs.catmarket.dto.outbound.OrderDtoOutbound
import labs.catmarket.dto.outbound.OrderItemDtoOutbound
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface OrderMapper {

    @Mapping(source = "ordersItems", target = "items")
    fun toDto(order: Order, @Context orderMapperHelper: OrderMapperHelper): OrderDtoOutbound

    fun toDtoItems(
        ordersItems: List<Order.OrdersItem>,
        @Context
        orderMapperHelper: OrderMapperHelper
    ): List<OrderItemDtoOutbound>

    @Mapping(target = "productName", expression = "java(orderMapperHelper.getProductNameFromId(ordersItem.getProductId()))")
    @Mapping(source = "pricePerUnit", target = "productPrice")
    @Mapping(source = "quantity", target = "quantity")
    fun toDtoItem(ordersItem: Order.OrdersItem, @Context orderMapperHelper: OrderMapperHelper): OrderItemDtoOutbound
}