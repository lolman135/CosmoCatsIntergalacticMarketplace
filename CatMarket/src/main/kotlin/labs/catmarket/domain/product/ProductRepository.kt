package labs.catmarket.domain.product

import labs.catmarket.domain.baseRepository.BaseRepository
import java.util.UUID

interface ProductRepository : BaseRepository<UUID, Product>