package labs.catmarket.usecase.useCase.category

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.category.CategoryRepository
import labs.catmarket.usecase.exception.DomainNotFoundException
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class GetCategoryByIdUseCase(private val categoryRepository: CategoryRepository) : UseCase<UUID, Category> {

    override fun execute(id: UUID) = categoryRepository.findById(id)
        ?: throw DomainNotFoundException("Category", id)

}