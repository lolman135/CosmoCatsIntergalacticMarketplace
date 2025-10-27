package labs.catmarket.usecase.useCase.category

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.repository.category.CategoryRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class DeleteCategoryByIdUseCase(private val categoryRepository: CategoryRepository) : UseCase<UUID, Unit> {
    override fun execute(id: UUID) = categoryRepository.deleteById(id)
}