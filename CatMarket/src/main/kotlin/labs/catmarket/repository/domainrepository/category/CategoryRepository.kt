package labs.catmarket.repository.domainrepository.category

import labs.catmarket.domain.Category
import labs.catmarket.repository.BaseRepository
import java.util.UUID

interface CategoryRepository : BaseRepository<UUID, Category> {
    fun existsByName(name: String): Boolean
}