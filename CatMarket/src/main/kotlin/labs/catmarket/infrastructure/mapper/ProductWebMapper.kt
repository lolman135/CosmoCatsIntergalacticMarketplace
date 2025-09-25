package labs.catmarket.infrastructure.mapper

import labs.catmarket.domain.product.Product
import labs.catmarket.infrastructure.dto.requet.busines.ProductDtoRequest
import labs.catmarket.infrastructure.dto.response.busines.ProductDtoResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ProductWebMapper {

    fun toDomain(request: ProductDtoRequest): Product

//    @Mapping(target = "category", expression = "java(categoryNameFromId(product.getCategoryId()))")
    fun toDto(product: Product): ProductDtoResponse
}