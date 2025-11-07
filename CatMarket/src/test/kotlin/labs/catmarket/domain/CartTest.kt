package labs.catmarket.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

class CartTest {

    private fun uuid(): UUID = UUID.randomUUID()

    @Test
    fun shouldAddNewProductWithQuantity() {
        val cart = Cart(userId = uuid())
        val productId = uuid()

        cart.addProduct(productId, 3)

        val items = cart.getItems()
        assertEquals(1, items.size)
        assertEquals(Cart.CartItem(productId, 3), items.first())
    }

    @Test
    fun shouldReplaceQuantityWhenAddingExistingProduct() {
        val cart = Cart(userId = uuid())
        val productId = uuid()

        cart.addProduct(productId, 2)
        cart.addProduct(productId, 5)

        val items = cart.getItems()
        assertEquals(1, items.size)
        assertEquals(5, items.first().quantity)
    }

    @Test
    fun shouldRemoveExistingProductAndReturnTrue() {
        val cart = Cart(userId = uuid())
        val p1 = uuid()
        val p2 = uuid()
        cart.addProduct(p1, 1)
        cart.addProduct(p2, 2)

        val removed = cart.removeProduct(p1)

        assertTrue(removed)
        val items = cart.getItems()
        assertEquals(1, items.size)
        assertEquals(p2, items.first().productId)
    }

    @Test
    fun shouldReturnFalseWhenRemovingAbsentProduct() {
        val cart = Cart(userId = uuid())
        val existing = uuid()
        val absent = uuid()
        cart.addProduct(existing, 4)

        val removed = cart.removeProduct(absent)

        assertFalse(removed)
        val items = cart.getItems()
        assertEquals(1, items.size)
        assertEquals(existing, items.first().productId)
        assertEquals(4, items.first().quantity)
    }

    @Test
    fun shouldClearAllItems() {
        val cart = Cart(userId = uuid())
        cart.addProduct(uuid(), 1)
        cart.addProduct(uuid(), 2)

        cart.clear()

        assertTrue(cart.getItems().isEmpty())
    }

    @Test
    fun shouldReturnSnapshotFromGetItemsUnaffectedByFutureChanges() {
        val cart = Cart(userId = uuid())
        val p1 = uuid()
        val p2 = uuid()
        cart.addProduct(p1, 1)

        val snapshotBefore = cart.getItems()
        cart.addProduct(p2, 2)

        assertEquals(1, snapshotBefore.size)
        assertEquals(2, cart.getItems().size)
        assertNotSame(snapshotBefore, cart.getItems())
    }
}