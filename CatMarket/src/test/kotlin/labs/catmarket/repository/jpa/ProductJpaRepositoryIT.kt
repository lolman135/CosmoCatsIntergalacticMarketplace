package labs.catmarket.repository.jpa

import labs.catmarket.AbstractIT
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.ProductJpaRepository
import labs.catmarket.repository.entity.CategoryEntity
import labs.catmarket.repository.entity.ProductEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class ProductJpaRepositoryIT @Autowired constructor(
    private val productJpaRepository: ProductJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository
) : AbstractIT() {

    @Test
    fun save_shouldPersistProduct() {
        val category = categoryJpaRepository.save(
            CategoryEntity(null, UUID.randomUUID(), "Cat-${UUID.randomUUID()}")
        )

        val product = ProductEntity(
            id = null,
            businessId = UUID.randomUUID(),
            name = "Laser Pizza",
            description = "Cheese with lasers",
            price = 150,
            imageUrl = "https://example.com/img.png",
            category = category
        )

        val saved = productJpaRepository.save(product)

        assertNotNull(saved.id)
        assertEquals("Laser Pizza", saved.name)
        assertEquals("Cheese with lasers", saved.description)
        assertEquals(150, saved.price)
        assertEquals(category.id, saved.category.id)
    }

    @Test
    fun existsProductEntityByName_shouldReturnTrueWhenExists() {
        val category = categoryJpaRepository.save(
            CategoryEntity(null, UUID.randomUUID(), "Cat-${UUID.randomUUID()}")
        )

        val product = ProductEntity(
            id = null,
            businessId = UUID.randomUUID(),
            name = "Galaxy Burger",
            description = "Mega burger",
            price = 200,
            imageUrl = "img",
            category = category
        )
        productJpaRepository.save(product)

        val exists = productJpaRepository.existsProductEntityByName("Galaxy Burger")

        assertTrue(exists)
    }

    @Test
    fun existsProductEntityByName_shouldReturnFalseWhenNotExists() {
        val exists = productJpaRepository.existsProductEntityByName("Unknown")
        assertFalse(exists)
    }

    @Test
    fun findByName_shouldReturnProjection() {
        val category = categoryJpaRepository.save(
            CategoryEntity(null, UUID.randomUUID(), "Cat-${UUID.randomUUID()}")
        )

        productJpaRepository.save(
            ProductEntity(
                id = null,
                businessId = UUID.randomUUID(),
                name = "Interstellar Soup",
                description = "Hot space broth",
                price = 99,
                imageUrl = "img",
                category = category
            )
        )

        val projection = productJpaRepository.findByName("Interstellar Soup").orElse(null)

        assertNotNull(projection)
        assertEquals("Interstellar Soup", projection.getName())
        assertEquals("Hot space broth", projection.getDescription())
        assertEquals(99, projection.getPrice())
    }

    @Test
    fun findByName_shouldReturnEmptyWhenNotExists() {
        val projection = productJpaRepository.findByName("NonExisting")
        assertTrue(projection.isEmpty)
    }
}
