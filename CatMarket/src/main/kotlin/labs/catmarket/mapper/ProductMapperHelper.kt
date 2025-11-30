package labs.catmarket.mapper

import labs.catmarket.application.useCase.category.GetCategoryByIdUseCase
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.exception.JpaEntityNotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProductMapperHelper(
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val categoryJpaRepository: CategoryJpaRepository
) {
    fun categoryNameFromId(categoryId: UUID) = getCategoryByIdUseCase.execute(categoryId).name

    fun getCategoryEntityFromId(categoryId: UUID) = categoryJpaRepository.findByNaturalId(categoryId).orElseThrow {
        JpaEntityNotFoundException("category", categoryId)
    }
}