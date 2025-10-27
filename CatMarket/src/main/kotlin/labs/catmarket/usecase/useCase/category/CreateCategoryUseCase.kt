package labs.catmarket.usecase.useCase.category

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.category.CategoryRepository
import labs.catmarket.usecase.exception.DomainAlreadyExistsException

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