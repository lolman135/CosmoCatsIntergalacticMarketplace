package labs.catmarket.mapper

import labs.catmarket.application.useCase.product.UpsertProductCommand
import labs.catmarket.domain.product.Product
import labs.catmarket.dto.inbound.ProductDtoInbound
import labs.catmarket.dto.outbound.ProductDtoOutbound
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [ProductMapperHelper::class])
interface ProductMapper {

    fun toCommand(request: ProductDtoInbound): UpsertProductCommand

    @Mapping(target = "category", expression = "java(productMapperHelper.categoryNameFromId(product.getCategoryId()))")
    fun toDto(product: Product, productMapperHelper: ProductMapperHelper): ProductDtoOutbound
}