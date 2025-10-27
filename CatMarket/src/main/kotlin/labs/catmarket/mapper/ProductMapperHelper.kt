package labs.catmarket.mapper

import labs.catmarket.application.useCase.category.GetCategoryByIdUseCase
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProductMapperHelper(
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase
) {
    fun categoryNameFromId(categoryId: UUID) = getCategoryByIdUseCase.execute(categoryId).name
}