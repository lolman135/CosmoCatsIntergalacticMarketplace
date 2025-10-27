package labs.catmarket.application.cart

import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.cart.AddProductCommand
import labs.catmarket.application.useCase.cart.AddProductToCartUseCase
import labs.catmarket.common.CartStorage
import labs.catmarket.domain.Cart
import labs.catmarket.repository.product.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import java.util.UUID

@ExtendWith(MockitoExtension::class)
@DisplayName("Add Product To Cart Use Case Test")
class AddProductToCartUseCaseTest {

    @Mock
    private lateinit var cartStorage: CartStorage

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var useCase: AddProductToCartUseCase

    @Test
    fun shouldAddProductToNewCartWhenCartDoesNotExist() {
        val userId = UUID.randomUUID()
        val productId = UUID.randomUUID()
        val command = AddProductCommand(userId = userId, productId = productId, quantity = 2)

        whenever(productRepository.existsById(productId)).thenReturn(true)
        whenever(cartStorage.findByUserId(userId)).thenReturn(null)

        useCase.execute(command)

        val captor = argumentCaptor<Cart>()
        verify(cartStorage).upsert(captor.capture())

        val savedCart = captor.firstValue
        assertEquals(userId, savedCart.userId)

        val items = savedCart.getItems()
        assertEquals(1, items.size)
        assertEquals(productId, items[0].productId)
        assertEquals(2, items[0].quantity)
    }

    @Test
    fun shouldAddProductToExistingCart() {
        val userId = UUID.randomUUID()
        val existingProductId = UUID.randomUUID()
        val newProductId = UUID.randomUUID()

        val existingCart = Cart(userId).apply { addProduct(existingProductId, 1) }
        val command = AddProductCommand(userId = userId, productId = newProductId, quantity = 3)

        whenever(productRepository.existsById(newProductId)).thenReturn(true)
        whenever(cartStorage.findByUserId(userId)).thenReturn(existingCart)

        useCase.execute(command)

        verify(cartStorage).upsert(existingCart)

        val items = existingCart.getItems()
        assertEquals(2, items.size)

        val newItem = items.firstOrNull { it.productId == newProductId }
        assertNotNull(newItem)
        assertEquals(3, newItem!!.quantity)
    }

    @Test
    fun shouldThrowDomainNotFoundExceptionIfProductDoesNotExist() {
        val userId = UUID.randomUUID()
        val missingProductId = UUID.randomUUID()
        val command = AddProductCommand(userId = userId, productId = missingProductId, quantity = 1)

        whenever(productRepository.existsById(missingProductId)).thenReturn(false)

        val ex = assertThrows(DomainNotFoundException::class.java) {
            useCase.execute(command)
        }

        assertEquals("Product with id=$missingProductId not found", ex.message)
        verifyNoInteractions(cartStorage)
    }
}
