package labs.catmarket.persistence.category

import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import org.springframework.stereotype.Repository
import java.util.UUID

//This repository will be replaced later by JPA Repository
@Repository
class CategoryMockRepository : CategoryRepository {

    private val categoryStorage: MutableMap<UUID, Category> = mutableMapOf()

    init {
        categoryStorage[UUID.fromString("d564bbc4-0158-47a1-ac98-38fb0a1f9400")] =
            Category(UUID.fromString("d564bbc4-0158-47a1-ac98-38fb0a1f9400"), "Space Cucumber")

        categoryStorage[UUID.fromString("dc7a64b5-eed7-47cf-ba2a-36592bba361e")] =
            Category(UUID.fromString("dc7a64b5-eed7-47cf-ba2a-36592bba361e"), "Cosmic Tacos")

        categoryStorage[UUID.fromString("86762808-661b-4e42-a832-694221ba616b")] =
            Category(UUID.fromString("86762808-661b-4e42-a832-694221ba616b"), "Astro Burrito")
    }

    override fun deleteById(id: UUID) {
        categoryStorage.remove(id)
    }

    override fun save(domain: Category): Category {
        categoryStorage[domain.id!!] = domain
        return domain
    }

    override fun findAll(): List<Category> = categoryStorage.map { it.value }.toList()

    override fun findById(id: UUID): Category? {
        return categoryStorage[id]
    }

    override fun existsById(id: UUID): Boolean {
        return categoryStorage.contains(id)
    }

    override fun existsByName(name: String) =
        categoryStorage.values.any { it.name.equals(name, ignoreCase = true)}

}