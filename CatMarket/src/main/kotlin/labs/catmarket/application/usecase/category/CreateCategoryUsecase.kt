package labs.catmarket.application.usecase.category

import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import java.util.UUID

class CreateCategoryUsecase(private val categoryRepository: CategoryRepository) {

    fun execute(domain: Category): Category{
        val domainWithId = domain.copy(id = UUID.randomUUID())
        return categoryRepository.save(domainWithId)
    }
}