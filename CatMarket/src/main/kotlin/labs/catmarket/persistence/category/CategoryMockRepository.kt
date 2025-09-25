package labs.catmarket.persistence.category

import labs.catmarket.domain.category.Category
import labs.catmarket.domain.category.CategoryRepository
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

    override fun save(category: Category): Category {
        val exists =categoryStorage.values.any { it.name.equals(category.name, ignoreCase = true) }
        if (exists)
            throw EntityAlreadyExistsException("This category is already exists")

        categoryStorage[category.id!!] = category
        return category
    }

    override fun findAll(): List<Category> = categoryStorage.map { it.value }.toList()

    override fun findById(id: UUID): Category {
        return categoryStorage[id]
            ?: throw EntityNotFoundException("Category with id=$id not found")
    }

    override fun existsById(id: UUID): Boolean {
        return categoryStorage.contains(id)
    }
}