package labs.catmarket.repository.jpa


import labs.catmarket.AbstractIT
import labs.catmarket.domain.Status
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.OrderJpaRepository
import labs.catmarket.repository.ProductJpaRepository
import labs.catmarket.repository.entity.CategoryEntity
import labs.catmarket.repository.entity.OrderEntity
import labs.catmarket.repository.entity.OrdersItemEntity
import labs.catmarket.repository.entity.ProductEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

class OrderJpaRepositoryIT @Autowired constructor(
    private val orderJpaRepository: OrderJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
    private val productJpaRepository: ProductJpaRepository
) : AbstractIT() {

    private fun createCategory(): CategoryEntity =
        categoryJpaRepository.save(
            CategoryEntity(
                id = null,
                businessId = UUID.randomUUID(),
                name = "Cat-${UUID.randomUUID().toString().take(30)}"
            )
        )

    private fun createProduct(category: CategoryEntity): ProductEntity =
        productJpaRepository.save(
            ProductEntity(
                id = null,
                businessId = UUID.randomUUID(),
                name = "Prod-${UUID.randomUUID().toString().take(30)}",
                description = "desc",
                price = 120,
                imageUrl = "img",
                category = category
            )
        )

    private fun createOrderWithItems(): OrderEntity {
        val category = createCategory()
        val product = createProduct(category)

        val order = OrderEntity(
            id = null,
            businessId = UUID.randomUUID(),
            createdAt = LocalDateTime.now(),
            status = Status.NEW,
            items = mutableListOf()
        )

        val item = OrdersItemEntity(
            id = null,
            product = product,
            pricePerUnit = product.price,
            quantity = 2,
            order = order
        )

        order.items += item
        return order
    }

    @Test
    fun save_shouldPersistOrderWithItems() {
        val order = createOrderWithItems()
        val saved = orderJpaRepository.save(order)

        assertNotNull(saved.id)
        assertEquals(order.businessId, saved.businessId)
        assertEquals(Status.NEW, saved.status)
        assertEquals(1, saved.items.size)

        val savedItem = saved.items.first()
        assertEquals(2, savedItem.quantity)
        assertEquals(saved.id, savedItem.order.id)
    }

    @Test
    @Transactional
    fun findByNaturalId_shouldReturnOrder() {
        val order = createOrderWithItems()
        val saved = orderJpaRepository.save(order)

        val found = orderJpaRepository.findByNaturalId(saved.businessId).orElse(null)

        assertNotNull(found)
        assertEquals(saved.businessId, found.businessId)
        assertEquals(saved.status, found.status)
        assertEquals(1, found.items.size)
    }

    @Test
    fun findByNaturalId_shouldReturnEmptyWhenNotExists() {
        val found = orderJpaRepository.findByNaturalId(UUID.randomUUID())
        assertTrue(found.isEmpty)
    }

    @Test
    fun save_shouldCascadePersistItems() {
        val order = createOrderWithItems()
        val saved = orderJpaRepository.save(order)

        assertEquals(1, saved.items.size)
        assertNotNull(saved.items.first().id)
    }
}
