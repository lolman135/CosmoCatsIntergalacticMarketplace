package labs.catmarket.persistence.product

import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import org.springframework.stereotype.Repository
import java.util.UUID

//This repository will be replaced later by JPA Repository
@Repository
class ProductMockRepository : ProductRepository{

    private val productHolder = mutableMapOf<UUID, Product>()

    init {
        val product1Id = UUID.randomUUID()
        productHolder[product1Id] = Product(
            id = product1Id,
            name = "Cucumber-9000 Pro Max",
            price = 1_000_000,
            description = "Super puper duper grooper cucumber with built-in rocket engine",
            imageUrl = "http://image.img",
            categoryId = UUID.fromString("d564bbc4-0158-47a1-ac98-38fb0a1f9400")
        )

        val product2Id = UUID.randomUUID()
        productHolder[product2Id] = Product(
            id = product2Id,
            name = "Galactic Tomato Supreme",
            price = 250,
            description = "Uber fresh cosmic tomato with protective layer of nanopolymer",
            imageUrl = "http://image2.img",
            categoryId = UUID.fromString("dc7a64b5-eed7-47cf-ba2a-36592bba361e")
        )

        val product3Id = UUID.randomUUID()
        productHolder[product3Id] = Product(
            id = product3Id,
            name = "Astro Onion 300",
            price = 500,
            description = "Onion engineered for interstellar travel",
            imageUrl = "http://image3.img",
            categoryId = UUID.fromString("86762808-661b-4e42-a832-694221ba616b")
        )
    }

    override fun deleteById(id: UUID) {
        productHolder.remove(id)
    }

    override fun save(domain: Product): Product {
        productHolder[domain.id!!] = domain
        return domain
    }

    override fun findById(id: UUID) = productHolder[id]

    override fun findAll() = productHolder.values.toList()

    override fun existsById(id: UUID) = productHolder.contains(id)

    override fun existsByName(name: String) =
        productHolder.values.any { it.name.equals(name, ignoreCase = true) }
}