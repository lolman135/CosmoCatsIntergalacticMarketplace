package labs.catmarket.usecase.useCase.cart

import labs.catmarket.usecase.exception.DomainNotFoundException
import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.common.CartStorage
import labs.catmarket.domain.Cart
import labs.catmarket.repository.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class AddProductToCartUseCase (
    private val cartStorage: CartStorage,
    private val productRepository: ProductRepository
): UseCase<AddProductCommand, Unit> {

    override fun execute(command: AddProductCommand) {
        if(!productRepository.existsById(command.productId))
            throw DomainNotFoundException("Product", command.productId)

        val cart = cartStorage.findByUserId(command.userId) ?: Cart(command.userId)
        cart.addProduct(command.productId, command.quantity)
        cartStorage.upsert(cart)
    }
}