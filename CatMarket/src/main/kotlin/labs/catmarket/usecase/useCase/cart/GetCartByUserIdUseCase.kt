package labs.catmarket.usecase.useCase.cart

import labs.catmarket.usecase.exception.CartNotFoundException
import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.domain.Cart
import labs.catmarket.common.CartStorage
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class GetCartByUserIdUseCase(
    private val cartStorage: CartStorage
) : UseCase<UUID, Cart> {
    override fun execute(userId: UUID) = cartStorage.findByUserId(userId)
        ?: throw CartNotFoundException()
}