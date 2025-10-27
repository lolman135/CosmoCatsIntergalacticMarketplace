package labs.catmarket.usecase.useCase.category

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.category.CategoryRepository
import org.springframework.stereotype.Service

@Service
class GetAllCategoriesUseCase(private val categoryRepository: CategoryRepository) : UseCase<Unit, List<Category>>{

    override fun execute(command: Unit) = categoryRepository.findAll()
}