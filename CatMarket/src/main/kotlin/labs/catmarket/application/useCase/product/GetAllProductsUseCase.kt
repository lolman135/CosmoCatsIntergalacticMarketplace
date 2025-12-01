package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Product
import labs.catmarket.repository.domainImpl.product.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllProductsUseCase(private val productRepository: ProductRepository) : UseCase<Unit, List<Product>> {

    @Transactional(readOnly = true)
    override fun execute(command: Unit) = productRepository.findAll()
}