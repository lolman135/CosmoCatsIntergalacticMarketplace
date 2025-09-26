package labs.catmarket.application.useCase.product

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.persistence.exception.EntityAlreadyExistsException
import labs.catmarket.persistence.exception.EntityNotFoundException
import java.util.UUID

class UpdateProductByIdUseCase(private val productRepository: ProductRepository)
    : UseCase<Pair<UUID, UpsertProductCommand>, Product>{

    override fun execute(command: Pair<UUID, UpsertProductCommand>): Product {
        val (id, executingCommand) = command

        val product = (productRepository.findById(id)
            ?: throw EntityNotFoundException("Product with id=$id not found"))

        if (product.name != executingCommand.name){
            if (productRepository.existsByName(executingCommand.name)){
                throw EntityAlreadyExistsException("This product is already exists")
            }
        }

        val updatedProduct = product
            .rename(executingCommand.name)
            .changePrice(executingCommand.price)
            .changeUrl(executingCommand.imageUrl)
            .copy(categoryId = executingCommand.categoryId)

        return productRepository.save(updatedProduct)
    }
}