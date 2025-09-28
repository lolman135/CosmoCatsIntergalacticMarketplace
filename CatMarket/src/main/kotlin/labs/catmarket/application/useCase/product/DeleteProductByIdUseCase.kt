package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.product.ProductRepository
import java.util.UUID

class DeleteProductByIdUseCase(private val productRepository: ProductRepository) : UseCase<UUID, Unit>{
    override fun execute(id: UUID) = productRepository.deleteById(id)
}