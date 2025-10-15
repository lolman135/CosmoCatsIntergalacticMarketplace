package labs.catmarket.repository.product

import labs.catmarket.domain.product.Product
import labs.catmarket.repository.BaseRepository
import java.util.UUID

interface ProductRepository : BaseRepository<UUID, Product> {
    fun existsByName(name: String): Boolean
}