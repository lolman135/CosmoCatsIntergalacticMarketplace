package labs.catmarket.persistence.product

import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import labs.catmarket.persistence.exception.EntityAlreadyExistsException
import labs.catmarket.persistence.exception.EntityNotFoundException
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProductMockRepository : ProductRepository{

    private val productStorage: MutableMap<UUID, Product> = mutableMapOf()

    override fun deleteById(id: UUID) {
        productStorage.remove(id)
    }


    override fun save(product: Product): Product {
        val exists = productStorage.values.any { it.name.equals(product.name, ignoreCase = true) }
        if (exists)
            throw EntityAlreadyExistsException("This product is already exists")

        productStorage[product.id!!] = product
        return product
    }

    override fun findById(id: UUID) = productStorage[id]
        ?: throw EntityNotFoundException("Product with Id: not found")

    override fun findAll() = productStorage.map { it.value }.toList()

    override fun existsById(id: UUID) = productStorage.contains(id)
}