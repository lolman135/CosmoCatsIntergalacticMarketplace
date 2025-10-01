package labs.catmarket.application.useCase.cart

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.cart.CartStorage
import java.util.UUID

class CleanCartForUserUseCase(
    private val cartStorage: CartStorage
) : UseCase <UUID, Unit> {
    override fun execute(userId: UUID) {
        cartStorage.findByUserId(userId)?.clear()
    }
}