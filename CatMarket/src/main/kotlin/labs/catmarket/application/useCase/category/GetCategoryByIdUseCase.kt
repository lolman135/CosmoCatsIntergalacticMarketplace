package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.domainrepository.category.CategoryRepository
import labs.catmarket.application.exception.DomainNotFoundException
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class GetCategoryByIdUseCase(private val categoryRepository: CategoryRepository) : UseCase<UUID, Category> {

    override fun execute(id: UUID) = categoryRepository.findById(id)
        ?: throw DomainNotFoundException("Category", id)

}