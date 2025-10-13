package labs.catmarket.application.useCase.cart

import labs.catmarket.application.exception.CartNotFoundException
import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.cart.Cart
import labs.catmarket.domain.cart.CartStorage
import java.util.UUID

class GetCartByUserIdUseCase(
    private val cartStorage: CartStorage
) : UseCase<UUID, Cart> {
    override fun execute(userId: UUID) = cartStorage.findByUserId(userId)
        ?: throw CartNotFoundException()
}