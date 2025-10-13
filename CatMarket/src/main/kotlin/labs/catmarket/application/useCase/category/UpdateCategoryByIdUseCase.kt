package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.exception.DomainNotFoundException
import java.util.UUID

class UpdateCategoryByIdUseCase(private val categoryRepository: CategoryRepository)
    : UseCase<Pair<UUID, UpsertCategoryCommand>, Category> {

    override fun execute(command: Pair<UUID, UpsertCategoryCommand>): Category{

        val (id, executingCommand) = command

        val category = categoryRepository.findById(id)
            ?: throw DomainNotFoundException("Category", id)

        if(category.name != executingCommand.name && categoryRepository.existsByName(executingCommand.name))
            throw DomainAlreadyExistsException("Category")

        val updatedCategory = category.rename(executingCommand.name)
        return categoryRepository.save(updatedCategory)
    }
}