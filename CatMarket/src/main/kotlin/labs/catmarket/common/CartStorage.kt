package labs.catmarket.common

import labs.catmarket.domain.Cart
import org.springframework.stereotype.Component
import java.util.UUID


//In my case the storage looks like a repository, but in the future, it will have different logic.
//Repositories will operate with jpa entities and change data and DB. Storage is going to be
//an in-memory logic component (or redis, if I will have enough time to integrate it into project,
//but sounds as good idea).
//So in that case, it continues to lay here (till next discussion about this topic) :)
@Component
class CartStorage {

    private val carts: MutableMap<UUID, Cart> = mutableMapOf()

    fun findByUserId(id: UUID): Cart? = carts[id]

    fun upsert(cart: Cart) {
        carts[cart.userId] = cart
    }

    fun clearAll() {
        carts.clear()
    }
}