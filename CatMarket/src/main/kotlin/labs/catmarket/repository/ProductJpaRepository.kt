package labs.catmarket.repository

import labs.catmarket.repository.projection.ProductDetailsProjection
import labs.catmarket.repository.entity.ProductEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface ProductJpaRepository : NaturalIdRepository<ProductEntity, UUID> {
    fun existsProductEntityByName(name: String): Boolean

    @Query("""
        SELECT  p.name AS name,
                p.description AS description,
                p.price AS price
        FROM ProductEntity p
        WHERE p.name = :name
    """)
    fun findByName(name: String): Optional<ProductDetailsProjection>
}