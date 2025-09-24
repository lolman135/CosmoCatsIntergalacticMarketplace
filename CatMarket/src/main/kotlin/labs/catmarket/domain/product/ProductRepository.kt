package labs.catmarket.domain.product

import java.util.UUID

interface ProductRepository {
    fun save(product: Product): Product
    fun getById(id: UUID): Product
    fun getAll(): List<Product>
    fun updateById(id: UUID, product: Product)
    fun deleteById(id: UUID)
}