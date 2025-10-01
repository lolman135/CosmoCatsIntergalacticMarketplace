package labs.catmarket.infrastructure.common

import labs.catmarket.domain.cart.Cart
import labs.catmarket.domain.cart.CartStorage
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CartStorageImpl : CartStorage {

    private val carts: MutableMap<UUID, Cart> = mutableMapOf()

    override fun findByUserId(id: UUID): Cart? = carts[id]

    override fun upsert(cart: Cart) {
        carts[cart.userId] = cart
    }

    override fun deleteByCustomerId(id: UUID) {
        carts.remove(id)
    }
}