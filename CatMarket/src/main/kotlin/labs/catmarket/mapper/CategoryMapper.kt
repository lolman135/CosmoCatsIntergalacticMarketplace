package labs.catmarket.mapper

import labs.catmarket.usecase.useCase.category.UpsertCategoryCommand
import labs.catmarket.domain.Category
import labs.catmarket.dto.inbound.CategoryDtoInbound
import labs.catmarket.dto.outbound.CategoryDtoOutbound
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CategoryMapper {

    fun toCommand(request: CategoryDtoInbound): UpsertCategoryCommand
    fun toDto(domain: Category): CategoryDtoOutbound
    fun toDtoList(domains: List<Category>): List<CategoryDtoOutbound>
}