package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.category.CategoryRepository
import java.util.UUID

class DeleteCategoryByIdUseCase(private val categoryRepository: CategoryRepository) : UseCase<UUID, Unit> {
    override fun execute(id: UUID) = categoryRepository.deleteById(id)
}