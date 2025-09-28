package labs.catmarket.application.useCase.cart

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.cart.Cart
import labs.catmarket.domain.cart.CartStorage

class AddProductToCartUseCase (
    private val cartStorage: CartStorage
): UseCase<AddProductCommand, Unit> {

    override fun execute(command: AddProductCommand) {
        val cart = cartStorage.findByUserId(command.userId) ?: Cart(command.userId)
        cart.addProduct(command.productId, command.quantity)
        cartStorage.upsert(cart)
    }
}