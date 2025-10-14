package labs.catmarket.repository.category

import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import org.springframework.stereotype.Repository
import java.util.UUID

//This repository will be replaced later by JPA Repository
@Repository
class CategoryMockRepository : CategoryRepository {

    private val categoryHolder = mutableMapOf<UUID, Category>()

    init {
        categoryHolder[UUID.fromString("d564bbc4-0158-47a1-ac98-38fb0a1f9400")] =
            Category(UUID.fromString("d564bbc4-0158-47a1-ac98-38fb0a1f9400"), "Space Cucumber")

        categoryHolder[UUID.fromString("dc7a64b5-eed7-47cf-ba2a-36592bba361e")] =
            Category(UUID.fromString("dc7a64b5-eed7-47cf-ba2a-36592bba361e"), "Cosmic Tomato")

        categoryHolder[UUID.fromString("86762808-661b-4e42-a832-694221ba616b")] =
            Category(UUID.fromString("86762808-661b-4e42-a832-694221ba616b"), "Astro Onion")
    }

    override fun deleteById(id: UUID) {
        categoryHolder.remove(id)
    }

    override fun save(domain: Category): Category {
        categoryHolder[domain.id!!] = domain
        return domain
    }

    override fun findAll(): List<Category> = categoryHolder.values.toList()

    override fun findById(id: UUID) = categoryHolder[id]


    override fun existsById(id: UUID): Boolean {
        return categoryHolder.contains(id)
    }

    override fun existsByName(name: String) =
        categoryHolder.values.any { it.name.equals(name, ignoreCase = true)}

}