package labs.catmarket.mapper

import labs.catmarket.domain.Cart
import labs.catmarket.dto.outbound.CartItemDtoOutbound
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CartMapper {
    @Mapping(target = "product", expression = "java(cartMapperHelper.getProductNameById(item.getProductId()))")
    @Mapping(target = "price", expression = "java(cartMapperHelper.getProductPriceById(item.getProductId()))")
    fun toDto(item: Cart.CartItem, cartMapperHelper: CartMapperHelper): CartItemDtoOutbound
}