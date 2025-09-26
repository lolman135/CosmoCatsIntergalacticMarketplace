package labs.catmarket.infrastructure.mapper

import labs.catmarket.application.useCase.category.UpsertCategoryCommand
import labs.catmarket.domain.category.Category
import labs.catmarket.infrastructure.dto.requet.busines.CategoryDtoRequest
import labs.catmarket.infrastructure.dto.response.busines.CategoryDtoResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CategoryWebMapper {

    fun toCommand(request: CategoryDtoRequest): UpsertCategoryCommand
    fun toDto(domain: Category): CategoryDtoResponse
    fun toDtoList(domains: List<Category>): List<CategoryDtoResponse>
}