package labs.catmarket.domain.cart

import java.util.UUID

data class Cart(
    val userId: UUID,
    private val items: MutableList<CartItem> = mutableListOf()
){

    data class CartItem(val productId: UUID, val quantity: Int)

    fun addProduct(productId: UUID, quantity: Int){
        val index = items.indexOfFirst { it.productId == productId }
        if(index >= 0){
            val existedItem = items[index]
            items[index] = existedItem.copy(quantity= existedItem.quantity + quantity)
        } else{
            items.add(CartItem(productId, quantity))
        }
    }

    fun removeProduct(productId: UUID) = items.removeIf { it.productId == productId }

    fun clear() = items.clear()

    fun getItems() = items.toList()
}