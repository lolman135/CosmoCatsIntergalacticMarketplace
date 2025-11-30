package labs.catmarket.mapper

import labs.catmarket.application.useCase.category.UpsertCategoryCommand
import labs.catmarket.domain.Category
import labs.catmarket.dto.inbound.CategoryDtoInbound
import labs.catmarket.dto.outbound.CategoryDtoOutbound
import labs.catmarket.repository.entity.CategoryEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CategoryMapper {

    fun toCommand(request: CategoryDtoInbound): UpsertCategoryCommand
    fun toDto(domain: Category): CategoryDtoOutbound
    fun toDtoList(domains: List<Category>): List<CategoryDtoOutbound>

    @Mapping(source = "id", target = "businessId")
    @Mapping(source = "name", target = "name")
    fun toEntityFromDomain(domain: Category): CategoryEntity

    @Mapping(source = "businessId", target = "id")
    @Mapping(source = "name", target = "name")
    fun toDomainFromEntity(entity: CategoryEntity): Category
}