package labs.catmarket.application.usecase.category

import labs.catmarket.domain.category.CategoryRepository
import java.util.UUID

class DeleteCategoryByIdUsecase(private val categoryRepository: CategoryRepository) {
    fun execute(id: UUID) = categoryRepository.deleteById(id)
}