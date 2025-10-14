package labs.catmarket.mapper

import labs.catmarket.application.useCase.category.UpsertCategoryCommand
import labs.catmarket.domain.category.Category
import labs.catmarket.dto.requet.busines.CategoryDtoRequest
import labs.catmarket.dto.response.CategoryDtoResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CategoryMapper {

    fun toCommand(request: CategoryDtoRequest): UpsertCategoryCommand
    fun toDto(domain: Category): CategoryDtoResponse
    fun toDtoList(domains: List<Category>): List<CategoryDtoResponse>
}