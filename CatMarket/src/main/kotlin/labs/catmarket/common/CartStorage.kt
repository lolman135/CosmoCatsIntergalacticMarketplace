package labs.catmarket.common

import labs.catmarket.domain.cart.Cart
import org.springframework.stereotype.Component
import java.util.UUID


//first, i dunno where to store this component, so it will lay here
@Component
class CartStorage {

    private val carts: MutableMap<UUID, Cart> = mutableMapOf()

    fun findByUserId(id: UUID): Cart? = carts[id]

    fun upsert(cart: Cart) {
        carts[cart.userId] = cart
    }

    fun deleteByCustomerId(id: UUID) {
        carts.remove(id)
    }
}