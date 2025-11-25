package labs.catmarket.repository.domainrepository.product

import labs.catmarket.domain.Product
import labs.catmarket.repository.BaseRepository
import java.util.UUID

interface ProductRepository : BaseRepository<UUID, Product> {
    fun existsByName(name: String): Boolean
}