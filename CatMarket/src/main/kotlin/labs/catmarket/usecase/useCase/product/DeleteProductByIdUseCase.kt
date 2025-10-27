package labs.catmarket.usecase.useCase.product

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.repository.product.ProductRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class DeleteProductByIdUseCase(private val productRepository: ProductRepository) : UseCase<UUID, Unit>{
    override fun execute(id: UUID) = productRepository.deleteById(id)
}