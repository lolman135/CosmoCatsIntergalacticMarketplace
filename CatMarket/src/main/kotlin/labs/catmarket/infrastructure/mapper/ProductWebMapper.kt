package labs.catmarket.infrastructure.mapper

import labs.catmarket.application.useCase.product.UpsertProductCommand
import labs.catmarket.domain.product.Product
import labs.catmarket.infrastructure.dto.requet.busines.ProductDtoRequest
import labs.catmarket.infrastructure.dto.response.ProductDtoResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [ProductWebMapperHelper::class])
interface ProductWebMapper {

    fun toCommand(request: ProductDtoRequest): UpsertProductCommand

    @Mapping(target = "category", expression = "java(productWebMapperHelper.categoryNameFromId(product.getCategoryId()))")
    fun toDto(product: Product, productWebMapperHelper: ProductWebMapperHelper): ProductDtoResponse
}