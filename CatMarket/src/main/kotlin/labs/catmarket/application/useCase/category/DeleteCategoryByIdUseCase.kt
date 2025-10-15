package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.repository.category.CategoryRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class DeleteCategoryByIdUseCase(private val categoryRepository: CategoryRepository) : UseCase<UUID, Unit> {
    override fun execute(id: UUID) = categoryRepository.deleteById(id)
}