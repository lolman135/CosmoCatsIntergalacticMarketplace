package labs.catmarket.domain.category

import labs.catmarket.domain.baseRepository.BaseRepository
import java.util.UUID

interface CategoryRepository : BaseRepository<UUID, Category> {
    fun existsByName(name: String): Boolean
}