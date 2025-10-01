package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.application.exception.EntityNotFoundException
import java.util.UUID

class GetProductByIdUseCase(private val productRepository: ProductRepository) : UseCase<UUID, Product>{
    override fun execute(id: UUID) = productRepository.findById(id)
        ?: throw EntityNotFoundException("Entity with id=$id not found")
}