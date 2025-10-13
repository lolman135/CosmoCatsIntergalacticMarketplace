package labs.catmarket.application.useCase.product

import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import java.util.*

class CreateProductUseCase(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : UseCase<UpsertProductCommand, Product> {

    override fun execute(command: UpsertProductCommand): Product {
        if (productRepository.existsByName(command.name))
            throw DomainAlreadyExistsException("product")

        if (!categoryRepository.existsById(command.categoryId))
            throw IllegalArgumentException("Wrong categoryId provided")

        val product = Product(
            id = UUID.randomUUID(),
            name = command.name,
            description = command.description,
            price = command.price,
            imageUrl = command.imageUrl,
            categoryId = command.categoryId
        )
        return productRepository.save(product)
    }
}