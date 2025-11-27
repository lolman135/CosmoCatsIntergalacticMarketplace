package labs.catmarket.repository

import labs.catmarket.repository.entity.ProductEntity
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductJpaRepository : NaturalIdRepository<ProductEntity, UUID> {
    fun existsProductEntityByName(name: String): Boolean
}