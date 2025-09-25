package labs.catmarket.application.usecase.category

import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.domain.product.Product

class GetAllCategoriesUsecase(private val categoryRepository: CategoryRepository) {

    fun execute() = categoryRepository.findAll()
}