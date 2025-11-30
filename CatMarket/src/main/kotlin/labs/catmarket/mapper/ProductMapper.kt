package labs.catmarket.mapper

import labs.catmarket.application.useCase.product.UpsertProductCommand
import labs.catmarket.domain.Category
import labs.catmarket.domain.Product
import labs.catmarket.dto.inbound.ProductDtoInbound
import labs.catmarket.dto.outbound.ProductDtoOutbound
import labs.catmarket.repository.entity.CategoryEntity
import labs.catmarket.repository.entity.ProductEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [ProductMapperHelper::class])
interface ProductMapper {

    fun toCommand(request: ProductDtoInbound): UpsertProductCommand

    @Mapping(target = "category", expression = "java(helper.categoryNameFromId(product.getCategoryId()))")
    fun toDto(product: Product, helper: ProductMapperHelper): ProductDtoOutbound

    @Mapping(source = "id", target = "businessId")
    @Mapping(target = "category", expression = "java(helper.getCategoryEntityFromId(domain.getCategoryId()))")
    fun toEntityFromDomain(domain: Product, helper: ProductMapperHelper): ProductEntity

    @Mapping(source = "businessId", target = "id")
    @Mapping(target = "categoryId", expression = "java(entity.getCategory().getBusinessId())")
    fun toDomainFromEntity(entity: ProductEntity): Product
}