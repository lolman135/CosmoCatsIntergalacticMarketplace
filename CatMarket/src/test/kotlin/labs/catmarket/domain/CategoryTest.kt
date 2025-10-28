package labs.catmarket.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

class CategoryTest {

    @Test
    fun shouldCreateCategoryWithValidName() {
        val id = UUID.randomUUID()
        val category = Category(id, "Cats")

        assertEquals(id, category.id)
        assertEquals("Cats", category.name)
    }

    @Test
    fun shouldThrowWhenNameIsBlankOnCreation() {
        val ex1 = assertThrows(IllegalArgumentException::class.java) { Category(null, "") }
        assertEquals("Name should not be empty", ex1.message)

        val ex2 = assertThrows(IllegalArgumentException::class.java) { Category(null, "   ") }
        assertEquals("Name should not be empty", ex2.message)
    }

    @Test
    fun shouldRenameToNewValidName() {
        val original = Category(id = UUID.randomUUID(), name = "Old")
        val renamed = original.rename("New")

        assertEquals("New", renamed.name)
        assertEquals(original.id, renamed.id)
    }

    @Test
    fun shouldThrowWhenRenameToBlank() {
        val category = Category(null, "Valid")

        val ex1 = assertThrows(IllegalArgumentException::class.java) { category.rename("") }
        assertEquals("Name should not be empty", ex1.message)

        val ex2 = assertThrows(IllegalArgumentException::class.java) { category.rename(" \t\n") }
        assertEquals("Name should not be empty", ex2.message)
    }
}