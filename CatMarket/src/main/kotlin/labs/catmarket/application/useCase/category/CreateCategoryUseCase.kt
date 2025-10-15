package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.category.Category
import labs.catmarket.repository.category.CategoryRepository
import labs.catmarket.application.exception.DomainAlreadyExistsException

import java.util.UUID
import org.springframework.stereotype.Service

@Service
class CreateCategoryUseCase(private val categoryRepository: CategoryRepository)
    : UseCase<UpsertCategoryCommand, Category> {

    override fun execute(command: UpsertCategoryCommand): Category{
        if (categoryRepository.existsByName(command.name))
            throw DomainAlreadyExistsException("Category")

        val category = Category(UUID.randomUUID(), command.name)

        return categoryRepository.save(category)
    }
}