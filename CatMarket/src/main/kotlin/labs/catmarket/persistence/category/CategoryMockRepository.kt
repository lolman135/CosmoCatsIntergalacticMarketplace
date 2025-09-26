package labs.catmarket.persistence.category

import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
import labs.catmarket.domain.product.Product
import labs.catmarket.persistence.exception.EntityAlreadyExistsException
import labs.catmarket.persistence.exception.EntityNotFoundException
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CategoryMockRepository : CategoryRepository {

    private val categoryStorage: MutableMap<UUID, Category> = mutableMapOf()

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