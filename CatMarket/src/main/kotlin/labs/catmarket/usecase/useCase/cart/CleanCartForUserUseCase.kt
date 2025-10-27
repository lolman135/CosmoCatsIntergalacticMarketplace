package labs.catmarket.usecase.useCase.cart

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.common.CartStorage
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class CleanCartForUserUseCase(
    private val cartStorage: CartStorage
) : UseCase <UUID, Unit> {
    override fun execute(userId: UUID) {
        cartStorage.findByUserId(userId)?.clear()
    }
}