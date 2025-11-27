package labs.catmarket.repository.domainImpl.category

import labs.catmarket.domain.Category
import labs.catmarket.repository.domainImpl.BaseRepository
import java.util.UUID

interface CategoryRepository : BaseRepository<UUID, Category> {
    fun existsByName(name: String): Boolean
}