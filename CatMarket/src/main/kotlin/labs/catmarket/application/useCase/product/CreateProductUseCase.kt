package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.persistence.exception.EntityAlreadyExistsException
import java.util.UUID

class CreateProductUseCase(private val productRepository: ProductRepository) : UseCase<UpsertProductCommand, Product>{

    override fun execute(command: UpsertProductCommand): Product {
        if(productRepository.existsByName(command.name))
            throw EntityAlreadyExistsException("This product is already exists")

        val product = Product(
            id = UUID.randomUUID(),
            name = command.name,
            price = command.price,
            imageUrl = command.imageUrl,
            categoryId = command.categoryId
        )
        return productRepository.save(product)
    }
}