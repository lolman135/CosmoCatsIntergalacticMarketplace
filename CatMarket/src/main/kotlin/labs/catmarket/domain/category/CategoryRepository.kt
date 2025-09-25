package labs.catmarket.domain.category

import labs.catmarket.domain.baseRepository.BaseRepository
import java.util.UUID

interface CategoryRepository : BaseRepository<UUID, Category>