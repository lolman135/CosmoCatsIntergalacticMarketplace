package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Product
import labs.catmarket.repository.domainImpl.product.ProductRepository
import labs.catmarket.application.exception.DomainNotFoundException
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetProductByIdUseCase(private val productRepository: ProductRepository) : UseCase<UUID, Product>{

    @Transactional(readOnly = true)
    override fun execute(id: UUID) = productRepository.findById(id)
        ?: throw DomainNotFoundException("Product", id)
}