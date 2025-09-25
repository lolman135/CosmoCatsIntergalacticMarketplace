package labs.catmarket.infrastructure.mapper

import labs.catmarket.domain.category.Category
import labs.catmarket.infrastructure.dto.requet.busines.CategoryDtoRequest
import labs.catmarket.infrastructure.dto.response.busines.CategoryDtoResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CategoryWebMapper {

    fun toDomain(request: CategoryDtoRequest): Category
    fun toDto(domain: Category): CategoryDtoResponse
}