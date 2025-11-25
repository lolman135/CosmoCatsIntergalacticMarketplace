package labs.catmarket.application.order

import labs.catmarket.application.exception.CartNotFoundException
import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.order.CreateOrderUseCase
import labs.catmarket.common.CartStorage
import labs.catmarket.domain.Cart
import labs.catmarket.domain.Product
import labs.catmarket.repository.domainrepository.order.OrderRepository
import labs.catmarket.repository.domainrepository.product.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.util.UUID

@ExtendWith(MockitoExtension::class)
@DisplayName("Create Order Use Case Test")
class CreateOrderUseCaseTest {

    @Mock
    private lateinit var orderRepository: OrderRepository

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var cartStorage: CartStorage

    @InjectMocks
    private lateinit var useCase: CreateOrderUseCase

    @Test
    fun shouldCreateOrderFromCartAndClearCart() {
        val userId = UUID.randomUUID()
        val productId = UUID.randomUUID()
        val product = Product(
            id = productId,
            name = "Cosmo cucumber",
            description = "Cucumber with built-in rocket engine",
            price = 1000000,
            imageUrl = "https://cucumber.img",
            categoryId = UUID.randomUUID()
        )

        val cart = mock<Cart> {
            on { getItems() } doReturn listOf(Cart.CartItem(productId, 2))
        }

        whenever(cartStorage.findByUserId(userId)).thenReturn(cart)
        whenever(productRepository.findById(productId)).thenReturn(product)

        whenever(orderRepository.save(any())).thenAnswer { invocation -> invocation.getArgument(0) }

        val saved = useCase.execute(userId)

        assertNotNull(saved.id)
        assertEquals(1, saved.orderItems.size)
        val item = saved.orderItems[0]
        assertEquals(productId, item.productId)
        assertEquals(2, item.quantity)
        assertEquals(product.price, item.pricePerUnit)

        verify(cart).clear()
        verify(orderRepository).save(any())
    }

    @Test
    fun shouldThrowCartNotFoundWhenMissing() {
        val userId = UUID.randomUUID()
        whenever(cartStorage.findByUserId(userId)).thenReturn(null)

        assertThrows(CartNotFoundException::class.java) {
            useCase.execute(userId)
        }

        verifyNoInteractions(productRepository, orderRepository)
    }

    @Test
    fun shouldThrowDomainNotFoundWhenProductMissing() {
        val userId = UUID.randomUUID()
        val productId = UUID.randomUUID()

        val cart = mock<Cart> {
            on { getItems() } doReturn listOf(Cart.CartItem(productId, 1))
        }

        whenever(cartStorage.findByUserId(userId)).thenReturn(cart)
        whenever(productRepository.findById(productId)).thenReturn(null)

        val ex = assertThrows(DomainNotFoundException::class.java) {
            useCase.execute(userId)
        }

        assertTrue(ex.message!!.contains("Product"))
        verify(orderRepository, never()).save(any())
        verify(cart, never()).clear()
    }
}
