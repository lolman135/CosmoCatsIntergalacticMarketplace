package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Product
import labs.catmarket.repository.domainImpl.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class GetAllProductsUseCase(private val productRepository: ProductRepository) : UseCase<Unit, List<Product>> {
    override fun execute(command: Unit) = productRepository.findAll()
}