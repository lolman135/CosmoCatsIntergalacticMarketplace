package labs.catmarket.persistence.product

import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProductMockRepository : ProductRepository{

    private val productStorage: MutableMap<UUID, Product> = mutableMapOf()

    override fun deleteById(id: UUID) {
        productStorage.remove(id)
    }

    override fun save(domain: Product): Product {
        productStorage[domain.id!!] = domain
        return domain
    }

    override fun findById(id: UUID) = productStorage[id]

    override fun findAll() = productStorage.map { it.value }.toList()

    override fun existsById(id: UUID) = productStorage.contains(id)

    override fun existsByName(name: String) =
        productStorage.values.any { it.name.equals(name, ignoreCase = true) }
}