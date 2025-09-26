package labs.catmarket.application.useCase.product

import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import java.util.UUID

class CreateProductUseCase(private val productRepository: ProductRepository) {

    fun execute(domain: Product): Product{
        val domainWithId = domain.copy(id = UUID.randomUUID())
        return productRepository.save(domainWithId)
    }
}