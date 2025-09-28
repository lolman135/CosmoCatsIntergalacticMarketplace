package labs.catmarket.persistence.product

import labs.catmarket.domain.product.Product
import labs.catmarket.domain.product.ProductRepository
import org.springframework.stereotype.Repository
import java.util.UUID

//This repository will be replaced later by JPA Repository
@Repository
class ProductMockRepository : ProductRepository{

    private val productStorage: MutableMap<UUID, Product> = mutableMapOf()

    init {
        val product1Id = UUID.randomUUID()
        productStorage[product1Id] = Product(
            id = product1Id,
            name = "Cucumber-9000 Pro Max",
            price = 1_000_000,
            description = "Super puper duper grooper cucumber with built-in rocket engine",
            imageUrl = "http://image.img",
            categoryId = UUID.fromString("d564bbc4-0158-47a1-ac98-38fb0a1f9400")
        )

        val product2Id = UUID.randomUUID()
        productStorage[product2Id] = Product(
            id = product2Id,
            name = "Galactic Taco Supreme",
            price = 250,
            description = "Taco filled with zero-gravity sauce and cosmic beans",
            imageUrl = "http://image2.img",
            categoryId = UUID.fromString("dc7a64b5-eed7-47cf-ba2a-36592bba361e")
        )

        val product3Id = UUID.randomUUID()
        productStorage[product3Id] = Product(
            id = product3Id,
            name = "Astro Burrito X",
            price = 500,
            description = "Burrito engineered for interstellar travel",
            imageUrl = "http://image3.img",
            categoryId = UUID.fromString("86762808-661b-4e42-a832-694221ba616b")
        )
    }

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