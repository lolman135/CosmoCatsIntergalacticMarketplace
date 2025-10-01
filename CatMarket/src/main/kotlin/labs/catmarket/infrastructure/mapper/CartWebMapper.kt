package labs.catmarket.infrastructure.mapper

import labs.catmarket.domain.cart.Cart
import labs.catmarket.infrastructure.dto.response.CartItemDtoResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CartWebMapper {
    @Mapping(target = "product", expression = "java(cartWebMapperHelper.getProductNameById(item.getProductId()))")
    @Mapping(target = "price", expression = "java(cartWebMapperHelper.getProductPriceById(item.getProductId()))")
    fun toDto(item: Cart.CartItem, cartWebMapperHelper: CartWebMapperHelper): CartItemDtoResponse
}