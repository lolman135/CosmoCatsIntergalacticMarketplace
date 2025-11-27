package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Product
import labs.catmarket.repository.domainImpl.product.ProductRepository
import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.repository.domainImpl.category.CategoryRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class UpdateProductByIdUseCase(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : UseCase<Pair<UUID, UpsertProductCommand>, Product>{

    override fun execute(command: Pair<UUID, UpsertProductCommand>): Product {
        val (id, executingCommand) = command

        val product = (productRepository.findById(id)
            ?: throw DomainNotFoundException("Product", id))

        if (productRepository.existsByName(executingCommand.name) && product.name != executingCommand.name)
            throw DomainAlreadyExistsException("product")

        if (!categoryRepository.existsById(executingCommand.categoryId))
            throw IllegalArgumentException("Wrong categoryId provided")

        val updatedProduct = product
            .rename(executingCommand.name)
            .changeDescription(executingCommand.description)
            .changePrice(executingCommand.price)
            .changeUrl(executingCommand.imageUrl)
            .copy(categoryId = executingCommand.categoryId)

        return productRepository.save(updatedProduct)
    }
}