package labs.catmarket.repository.category

import labs.catmarket.domain.category.Category
import labs.catmarket.repository.BaseRepository
import java.util.UUID

interface CategoryRepository : BaseRepository<UUID, Category> {
    fun existsByName(name: String): Boolean
}