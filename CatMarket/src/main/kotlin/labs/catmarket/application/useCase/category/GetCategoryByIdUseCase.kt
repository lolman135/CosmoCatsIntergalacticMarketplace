package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.application.exception.EntityNotFoundException
import java.util.UUID

class GetCategoryByIdUseCase(private val categoryRepository: CategoryRepository) : UseCase<UUID, Category> {

    override fun execute(command: UUID) = categoryRepository.findById(command)
        ?: throw EntityNotFoundException("Category with id=$command not found")

}