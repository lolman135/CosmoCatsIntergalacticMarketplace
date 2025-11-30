package labs.catmarket.repository.domainImpl.product

import labs.catmarket.domain.Product
import labs.catmarket.repository.domainImpl.BaseRepository
import labs.catmarket.repository.projection.ProductDetailsProjection
import java.util.UUID

interface ProductRepository : BaseRepository<UUID, Product> {
    fun existsByName(name: String): Boolean

    fun findProjectionByName(name: String): ProductDetailsProjection?
}