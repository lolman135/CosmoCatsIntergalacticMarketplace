package labs.catmarket.repository.jpa

import labs.catmarket.AbstractIT
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.entity.CategoryEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class CategoryJpaRepositoryIT @Autowired constructor(
    private val categoryJpaRepository: CategoryJpaRepository
) : AbstractIT() {

    @Test
    fun save_shouldPersistCategory() {
        val businessId = UUID.randomUUID()
        val entity = CategoryEntity(null, businessId, "Space Burgers")

        val saved = categoryJpaRepository.save(entity)

        assertNotNull(saved.id)
        assertEquals("Space Burgers", saved.name)
        assertEquals(businessId, saved.businessId)
    }

    @Test
    fun existsCategoryEntityByName_shouldReturnTrueWhenExists() {
        val entity = CategoryEntity(null, UUID.randomUUID(), "Cosmic Fries")
        categoryJpaRepository.save(entity)

        val exists = categoryJpaRepository.existsCategoryEntityByName("Cosmic Fries")

        assertTrue(exists)
    }

    @Test
    fun existsCategoryEntityByName_shouldReturnFalseWhenNotExists() {
        val exists = categoryJpaRepository.existsCategoryEntityByName("Unknown")
        assertFalse(exists)
    }

    @Test
    fun findByNaturalId_shouldReturnEntity() {
        val businessId = UUID.randomUUID()
        val entity = CategoryEntity(null, businessId, "Astro Meals")
        categoryJpaRepository.save(entity)

        val found = categoryJpaRepository.findByNaturalId(businessId).orElse(null)

        assertNotNull(found)
        assertEquals("Astro Meals", found?.name)
        assertEquals(businessId, found?.businessId)
    }

    @Test
    fun findByNaturalId_shouldReturnEmptyWhenNotExists() {
        val found = categoryJpaRepository.findByNaturalId(UUID.randomUUID())
        assertTrue(found.isEmpty)
    }
}
