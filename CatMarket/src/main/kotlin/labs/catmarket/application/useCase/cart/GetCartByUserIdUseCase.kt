package labs.catmarket.application.useCase.cart

import labs.catmarket.application.exception.EntityNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.cart.Cart
import labs.catmarket.domain.cart.CartStorage
import java.util.UUID

class GetCartByUserIdUseCase(
    private val cartStorage: CartStorage
) : UseCase<UUID, Cart> {
    override fun execute(userId: UUID) = cartStorage.findByUserId(userId)
        ?: throw EntityNotFoundException("Cart for this user not found")
}