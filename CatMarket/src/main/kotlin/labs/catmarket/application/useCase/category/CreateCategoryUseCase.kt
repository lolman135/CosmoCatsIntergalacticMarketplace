package labs.catmarket.application.useCase.category


import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.domainImpl.category.CategoryRepository
import labs.catmarket.application.exception.DomainAlreadyExistsException

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class CreateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) : UseCase<UpsertCategoryCommand, Category> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun execute(command: UpsertCategoryCommand): Category{
        if (categoryRepository.existsByName(command.name))
            throw DomainAlreadyExistsException("Category")

        val category = Category(UUID.randomUUID(), command.name)

        return categoryRepository.save(category)
    }
}