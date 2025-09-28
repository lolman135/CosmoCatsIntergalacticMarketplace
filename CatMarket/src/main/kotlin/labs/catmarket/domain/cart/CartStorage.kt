package labs.catmarket.domain.cart

import java.util.UUID

interface CartStorage {
    fun findByUserId(id: UUID): Cart?
    fun upsert(cart: Cart)
    fun deleteByCustomerId(id: UUID)
}