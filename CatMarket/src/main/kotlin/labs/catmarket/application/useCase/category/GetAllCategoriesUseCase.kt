package labs.catmarket.application.useCase.category

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.domainImpl.category.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllCategoriesUseCase(private val categoryRepository: CategoryRepository) : UseCase<Unit, List<Category>>{

    @Transactional(readOnly = true)
    override fun execute(command: Unit) = categoryRepository.findAll()
}