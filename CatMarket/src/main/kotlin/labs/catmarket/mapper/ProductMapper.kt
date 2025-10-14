package labs.catmarket.mapper

import labs.catmarket.application.useCase.product.UpsertProductCommand
import labs.catmarket.domain.product.Product
import labs.catmarket.dto.requet.busines.ProductDtoRequest
import labs.catmarket.dto.response.ProductDtoResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [ProductMapperHelper::class])
interface ProductMapper {

    fun toCommand(request: ProductDtoRequest): UpsertProductCommand

    @Mapping(target = "category", expression = "java(productMapperHelper.categoryNameFromId(product.getCategoryId()))")
    fun toDto(product: Product, productMapperHelper: ProductMapperHelper): ProductDtoResponse
}