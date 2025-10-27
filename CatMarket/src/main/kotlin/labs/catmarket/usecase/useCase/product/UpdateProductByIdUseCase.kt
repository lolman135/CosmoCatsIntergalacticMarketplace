package labs.catmarket.usecase.useCase.product

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.domain.Product
import labs.catmarket.repository.product.ProductRepository
import labs.catmarket.usecase.exception.DomainAlreadyExistsException
import labs.catmarket.usecase.exception.DomainNotFoundException
import labs.catmarket.repository.category.CategoryRepository
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