package labs.catmarket.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

class ProductTest {

    private fun validProduct(): Product = Product(
        id = UUID.randomUUID(),
        name = "Cat Toy",
        description = "Fun toy",
        price = 100,
        imageUrl = "http://img",
        categoryId = UUID.randomUUID()
    )

    @Test
    fun shouldCreateProductWithValidFields() {
        val id = UUID.randomUUID()
        val categoryId = UUID.randomUUID()
        val p = Product(id, "Name", "Desc", 10, "http://img", categoryId)

        assertEquals(id, p.id)
        assertEquals("Name", p.name)
        assertEquals("Desc", p.description)
        assertEquals(10, p.price)
        assertEquals("http://img", p.imageUrl)
        assertEquals(categoryId, p.categoryId)
    }

    @Test
    fun shouldThrowWhenNameIsBlankOnCreation() {
        val ex1 = assertThrows(IllegalArgumentException::class.java) {
            Product(null, "", "d", 1, "http://img", UUID.randomUUID())
        }
        assertEquals("Name should not be empty", ex1.message)

        val ex2 = assertThrows(IllegalArgumentException::class.java) {
            Product(null, "   ", "d", 1, "http://img", UUID.randomUUID())
        }
        assertEquals("Name should not be empty", ex2.message)
    }

    @Test
    fun shouldThrowWhenPriceIsNegativeOnCreation() {
        val ex = assertThrows(IllegalArgumentException::class.java) {
            Product(null, "Name", "d", -1, "http://img", UUID.randomUUID())
        }
        assertEquals("Price should not be less than 0", ex.message)
    }

    @Test
    fun shouldThrowWhenImageUrlIsBlankOnCreation() {
        val ex1 = assertThrows(IllegalArgumentException::class.java) {
            Product(null, "Name", "d", 1, "", UUID.randomUUID())
        }
        assertEquals("Url should not be empty", ex1.message)

        val ex2 = assertThrows(IllegalArgumentException::class.java) {
            Product(null, "Name", "d", 1, "   ", UUID.randomUUID())
        }
        assertEquals("Url should not be empty", ex2.message)
    }

    @Test
    fun shouldRenameToValidNameKeepingOtherFields() {
        val p = validProduct()
        val renamed = p.rename("New Name")

        assertEquals("New Name", renamed.name)
        assertEquals(p.description, renamed.description)
        assertEquals(p.price, renamed.price)
        assertEquals(p.imageUrl, renamed.imageUrl)
        assertEquals(p.id, renamed.id)
        assertEquals(p.categoryId, renamed.categoryId)
    }

    @Test
    fun shouldThrowWhenRenameToBlank() {
        val p = validProduct()
        val ex1 = assertThrows(IllegalArgumentException::class.java) { p.rename("") }
        assertEquals("Name should not be empty", ex1.message)

        val ex2 = assertThrows(IllegalArgumentException::class.java) { p.rename("  ") }
        assertEquals("Name should not be empty", ex2.message)
    }

    @Test
    fun shouldChangeDescriptionToValidKeepingOtherFields() {
        val p = validProduct()
        val changed = p.changeDescription("New Description")

        assertEquals("New Description", changed.description)
        assertEquals(p.name, changed.name)
        assertEquals(p.price, changed.price)
        assertEquals(p.imageUrl, changed.imageUrl)
        assertEquals(p.id, changed.id)
        assertEquals(p.categoryId, changed.categoryId)
    }

    @Test
    fun shouldThrowWhenChangeDescriptionToBlank() {
        val p = validProduct()
        val ex1 = assertThrows(IllegalArgumentException::class.java) { p.changeDescription("") }
        assertEquals("Description should not be empty", ex1.message)

        val ex2 = assertThrows(IllegalArgumentException::class.java) { p.changeDescription(" \t") }
        assertEquals("Description should not be empty", ex2.message)
    }

    @Test
    fun shouldChangePriceToZeroOrPositiveKeepingOtherFields() {
        val p = validProduct()

        val zero = p.changePrice(0)
        assertEquals(0, zero.price)
        assertEquals(p.name, zero.name)
        assertEquals(p.description, zero.description)
        assertEquals(p.imageUrl, zero.imageUrl)
        assertEquals(p.id, zero.id)
        assertEquals(p.categoryId, zero.categoryId)

        val positive = p.changePrice(42)
        assertEquals(42, positive.price)
        assertEquals(p.name, positive.name)
        assertEquals(p.description, positive.description)
        assertEquals(p.imageUrl, positive.imageUrl)
        assertEquals(p.id, positive.id)
        assertEquals(p.categoryId, positive.categoryId)
    }

    @Test
    fun shouldThrowWhenChangePriceToNegative() {
        val p = validProduct()
        val ex = assertThrows(IllegalArgumentException::class.java) { p.changePrice(-5) }
        assertEquals("Price should not be less than 0", ex.message)
    }

    @Test
    fun shouldChangeUrlToValidKeepingOtherFields() {
        val p = validProduct()
        val changed = p.changeUrl("http://new-img")

        assertEquals("http://new-img", changed.imageUrl)
        assertEquals(p.name, changed.name)
        assertEquals(p.description, changed.description)
        assertEquals(p.price, changed.price)
        assertEquals(p.id, changed.id)
        assertEquals(p.categoryId, changed.categoryId)
    }

    @Test
    fun shouldThrowWhenChangeUrlToBlank() {
        val p = validProduct()
        val ex1 = assertThrows(IllegalArgumentException::class.java) { p.changeUrl("") }
        assertEquals("Url should not be empty", ex1.message)

        val ex2 = assertThrows(IllegalArgumentException::class.java) { p.changeUrl("  ") }
        assertEquals("Url should not be empty", ex2.message)
    }
}
