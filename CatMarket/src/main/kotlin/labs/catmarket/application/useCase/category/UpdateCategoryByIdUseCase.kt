package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.persistence.exception.EntityAlreadyExistsException
import labs.catmarket.persistence.exception.EntityNotFoundException
import java.util.UUID

class UpdateCategoryByIdUseCase(private val categoryRepository: CategoryRepository)
    : UseCase<Pair<UUID, UpsertCategoryCommand>, Category> {

    override fun execute(command: Pair<UUID, UpsertCategoryCommand>): Category{

        val (id, executingCommand) = command

        val category = categoryRepository.findById(id)
            ?: throw EntityNotFoundException("Category with id=$id not found")

        if(category.name != executingCommand.name) {
            if (categoryRepository.existsByName(executingCommand.name)){
                throw EntityAlreadyExistsException("This Category already exists")
            }
        }

        val updatedCategory = category.rename(executingCommand.name)
        return categoryRepository.save(updatedCategory)
    }
}