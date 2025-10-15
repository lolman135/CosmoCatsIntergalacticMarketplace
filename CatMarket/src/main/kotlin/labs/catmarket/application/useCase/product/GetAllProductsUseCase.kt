package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.product.Product
import labs.catmarket.repository.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class GetAllProductsUseCase(private val productRepository: ProductRepository) : UseCase<Unit, List<Product>> {
    override fun execute(command: Unit) = productRepository.findAll()
}