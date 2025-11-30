package labs.catmarket.application.useCase.category


import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.domainImpl.category.CategoryRepository
import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.exception.DomainNotFoundException
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateCategoryByIdUseCase(
    private val categoryRepository: CategoryRepository
) : UseCase<Pair<UUID, UpsertCategoryCommand>, Category> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
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