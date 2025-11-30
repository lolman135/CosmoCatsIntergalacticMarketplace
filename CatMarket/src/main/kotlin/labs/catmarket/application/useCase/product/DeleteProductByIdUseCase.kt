package labs.catmarket.application.useCase.product

import jakarta.transaction.Transactional
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.repository.domainImpl.product.ProductRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class DeleteProductByIdUseCase(private val productRepository: ProductRepository) : UseCase<UUID, Unit>{

    @Transactional
    override fun execute(id: UUID) = productRepository.deleteById(id)
}