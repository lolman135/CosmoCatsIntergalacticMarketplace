package labs.catmarket.application.usecase.category

import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import java.util.UUID

class GetCategoryByIdUsecase(private val categoryRepository: CategoryRepository) {

    fun execute(id: UUID): Category {
        return categoryRepository.findById(id)
    }
}