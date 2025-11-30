package labs.catmarket.mapper

import labs.catmarket.domain.Order
import labs.catmarket.domain.Status
import labs.catmarket.mapper.impl.CustomOrderEntityMapperImpl
import labs.catmarket.repository.ProductJpaRepository
import labs.catmarket.repository.entity.CategoryEntity
import labs.catmarket.repository.entity.OrderEntity
import labs.catmarket.repository.entity.OrdersItemEntity
import labs.catmarket.repository.entity.ProductEntity
import labs.catmarket.repository.exception.JpaEntityNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
@DisplayName("CustomOrderEntityMapper Test")
class CustomOrderEntityMapperTest {

    @Mock
    private lateinit var productJpaRepository: ProductJpaRepository

    @InjectMocks
    private lateinit var mapper: CustomOrderEntityMapperImpl

    private lateinit var category: CategoryEntity
    private lateinit var product: ProductEntity
    private lateinit var productId: UUID

    @BeforeEach
    fun setUp() {
        category = CategoryEntity(
            id = 1L,
            businessId = UUID.randomUUID(),
            name = "Category"
        )

        productId = UUID.randomUUID()

        product = ProductEntity(
            id = 10L,
            businessId = productId,
            name = "Test",
            description = "Desc",
            price = 100,
            imageUrl = "img.png",
            category = category
        )
    }

    @Test
    fun shouldMapEntityToDomain() {
        val orderEntity = OrderEntity(
            id = 1L,
            businessId = UUID.randomUUID(),
            createdAt = LocalDateTime.now(),
            status = Status.NEW
        )

        val itemEntity = OrdersItemEntity(
            id = 5L,
            product = product,
            pricePerUnit = 100,
            quantity = 3,
            order = orderEntity
        )

        orderEntity.items = mutableListOf(itemEntity)

        val domain = mapper.toDomainFromEntity(orderEntity)

        assertEquals(orderEntity.businessId, domain.id)
        assertEquals(orderEntity.createdAt, domain.creationTime)
        assertEquals(Status.NEW, domain.status)
        assertEquals(1, domain.ordersItems.size)

        val item = domain.ordersItems[0]
        assertEquals(productId, item.productId)
        assertEquals(3, item.quantity)
        assertEquals(100, item.pricePerUnit)
    }

    @Test
    fun shouldMapDomainToEntity() {
        whenever(productJpaRepository.findByNaturalId(productId))
            .thenReturn(Optional.of(product))

        val domain = Order(
            id = UUID.randomUUID(),
            creationTime = LocalDateTime.now(),
            status = Status.PAID,
            ordersItems = listOf(
                Order.OrdersItem(
                    productId = productId,
                    quantity = 5,
                    pricePerUnit = 300
                )
            )
        )

        val entity = mapper.toEntityFromDomain(domain)

        assertEquals(domain.id, entity.businessId)
        assertEquals(domain.creationTime, entity.createdAt)
        assertEquals(domain.status, entity.status)
        assertEquals(1, entity.items.size)

        val item = entity.items[0]
        assertEquals(product, item.product)
        assertEquals(5, item.quantity)
        assertEquals(300, item.pricePerUnit)
        assertEquals(entity, item.order)

        verify(productJpaRepository).findByNaturalId(productId)
        verifyNoMoreInteractions(productJpaRepository)
    }

    @Test
    fun shouldThrowExceptionWhenProductNotFound() {
        whenever(productJpaRepository.findByNaturalId(productId))
            .thenReturn(Optional.empty())

        val domain = Order(
            id = UUID.randomUUID(),
            creationTime = LocalDateTime.now(),
            status = Status.NEW,
            ordersItems = listOf(
                Order.OrdersItem(
                    productId = productId,
                    quantity = 1,
                    pricePerUnit = 50
                )
            )
        )

        val exception = assertThrows(JpaEntityNotFoundException::class.java) {
            mapper.toEntityFromDomain(domain)
        }

        assertTrue(exception.message.contains("Product"))
        assertTrue(exception.message.contains(productId.toString()))

        verify(productJpaRepository).findByNaturalId(productId)
        verifyNoMoreInteractions(productJpaRepository)
    }
}
