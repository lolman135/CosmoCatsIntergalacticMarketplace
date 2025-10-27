package labs.catmarket.usecase.useCase.category

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.category.CategoryRepository
import labs.catmarket.usecase.exception.DomainAlreadyExistsException
import labs.catmarket.usecase.exception.DomainNotFoundException
import java.util.UUID
import org.springframework.stereotype.Service

@Service
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