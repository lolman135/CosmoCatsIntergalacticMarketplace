package labs.catmarket.application.useCase.product

import labs.catmarket.application.exception.ProjectionNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.dto.outbound.ProductProjectionDtoOutbound
import labs.catmarket.repository.domainImpl.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class GetProductProjectionByNameUseCase(
    private val productRepository: ProductRepository
) : UseCase<String, ProductProjectionDtoOutbound>{

    override fun execute(command: String): ProductProjectionDtoOutbound {
        val projection = productRepository.findProjectionByName(command)
            ?: throw ProjectionNotFoundException(command)

        return ProductProjectionDtoOutbound(
            name = projection.getName(),
            description = projection.getDescription(),
            price = projection.getPrice()
        )
    }
}