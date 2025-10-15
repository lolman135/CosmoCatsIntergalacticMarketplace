package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Product
import labs.catmarket.repository.product.ProductRepository
import labs.catmarket.application.exception.DomainNotFoundException
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class GetProductByIdUseCase(private val productRepository: ProductRepository) : UseCase<UUID, Product>{
    override fun execute(id: UUID) = productRepository.findById(id)
        ?: throw DomainNotFoundException("Product", id)
}