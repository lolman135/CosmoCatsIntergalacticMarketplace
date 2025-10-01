package labs.catmarket.application.useCase.cart

import labs.catmarket.application.exception.EntityNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.cart.Cart
import labs.catmarket.domain.cart.CartStorage
import labs.catmarket.domain.product.ProductRepository

class AddProductToCartUseCase (
    private val cartStorage: CartStorage,
    private val productRepository: ProductRepository
): UseCase<AddProductCommand, Unit> {

    override fun execute(command: AddProductCommand) {
        if(!productRepository.existsById(command.productId))
            throw EntityNotFoundException("Product with id=${command.productId} not found")

        val cart = cartStorage.findByUserId(command.userId) ?: Cart(command.userId)
        cart.addProduct(command.productId, command.quantity)
        cartStorage.upsert(cart)
    }
}