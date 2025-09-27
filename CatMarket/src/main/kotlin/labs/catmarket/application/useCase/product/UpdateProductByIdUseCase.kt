package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.application.exception.EntityAlreadyExistsException
import labs.catmarket.application.exception.EntityNotFoundException
import labs.catmarket.domain.category.CategoryRepository
import java.util.UUID

class UpdateProductByIdUseCase(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : UseCase<Pair<UUID, UpsertProductCommand>, Product>{

    override fun execute(command: Pair<UUID, UpsertProductCommand>): Product {
        val (id, executingCommand) = command

        val product = (productRepository.findById(id)
            ?: throw EntityNotFoundException("Product with id=$id not found"))

        if (productRepository.existsByName(executingCommand.name) && product.name != executingCommand.name)
            throw EntityAlreadyExistsException("This product is already exists")

        if (!categoryRepository.existsById(executingCommand.categoryId))
            throw IllegalArgumentException("Wrong categoryId provided")

        val updatedProduct = product
            .rename(executingCommand.name)
            .changePrice(executingCommand.price)
            .changeUrl(executingCommand.imageUrl)
            .copy(categoryId = executingCommand.categoryId)

        return productRepository.save(updatedProduct)
    }
}