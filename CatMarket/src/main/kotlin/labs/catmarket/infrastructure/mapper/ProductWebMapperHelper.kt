package labs.catmarket.infrastructure.mapper

import labs.catmarket.application.useCase.category.GetCategoryByIdUseCase
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProductWebMapperHelper(
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase
) {
    fun categoryNameFromId(categoryId: UUID): String {
        val category = getCategoryByIdUseCase.execute(categoryId)
        return category.name
    }
}