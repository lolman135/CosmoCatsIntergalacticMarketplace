package labs.catmarket.repository

import labs.catmarket.repository.entity.CategoryEntity
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryJpaRepository : NaturalIdRepository<CategoryEntity, UUID> {
    fun existsCategoryEntityByName(name: String): Boolean
}