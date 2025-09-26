package labs.catmarket.application.useCase.product

import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.persistence.exception.EntityNotFoundException
import java.util.UUID

class UpdateProductByIdUseCase(private val productRepository: ProductRepository) {
}