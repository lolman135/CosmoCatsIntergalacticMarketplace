package labs.catmarket.application.usecase.product

import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import java.util.UUID

class CreateProductUsecase(private val productRepository: ProductRepository) {

    fun execute(domain: Product): Product{
        val domainWithId = domain.copy(id = UUID.randomUUID())
        return productRepository.save(domainWithId)
    }
}