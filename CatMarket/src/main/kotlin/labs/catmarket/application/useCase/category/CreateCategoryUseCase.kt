package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.application.exception.EntityAlreadyExistsException

import java.util.UUID

class CreateCategoryUseCase(private val categoryRepository: CategoryRepository)
    : UseCase<UpsertCategoryCommand, Category> {

    override fun execute(command: UpsertCategoryCommand): Category{
        if (categoryRepository.existsByName(command.name))
            throw EntityAlreadyExistsException("This Category already exists")

        val category = Category(UUID.randomUUID(), command.name)

        return categoryRepository.save(category)
    }
}