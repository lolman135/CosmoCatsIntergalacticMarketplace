package labs.catmarket.infrastructure.mapper

import labs.catmarket.application.useCase.category.GetCategoryByIdUseCase
import labs.catmarket.application.useCase.product.UpsertProductCommand
import labs.catmarket.domain.product.Product
import labs.catmarket.infrastructure.dto.requet.busines.ProductDtoRequest
import labs.catmarket.infrastructure.dto.response.busines.ProductDtoResponse
import org.mapstruct.IterableMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.springframework.stereotype.Component
import java.util.UUID

@Mapper(componentModel = "spring", uses = [ProductWebMapperHelper::class])
interface ProductWebMapper {

    fun toCommand(request: ProductDtoRequest): UpsertProductCommand

    @Named("toDtoWithHelper")
    @Mapping(target = "category", expression = "java(productWebMapperHelper.categoryNameFromId(product.getCategoryId()))")
    fun toDto(product: Product, productWebMapperHelper: ProductWebMapperHelper): ProductDtoResponse
}