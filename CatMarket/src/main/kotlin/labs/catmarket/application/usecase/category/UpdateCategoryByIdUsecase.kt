package labs.catmarket.application.usecase.category

import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.persistence.exception.EntityNotFoundException
import java.util.UUID

class UpdateCategoryByIdUsecase(private val categoryRepository: CategoryRepository) {

    fun execute(id: UUID, category: Category): Category{
        if (!categoryRepository.existsById(id))
            throw EntityNotFoundException("Entity with id=$id not found")

        val updatedCategory = category.copy(id = id)
        return categoryRepository.save(updatedCategory)
    }
}