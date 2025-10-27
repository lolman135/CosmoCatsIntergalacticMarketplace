package labs.catmarket.usecase.cart

import labs.catmarket.usecase.useCase.cart.CleanCartForUserUseCase
import labs.catmarket.common.CartStorage
import labs.catmarket.domain.Cart
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class CleanCartForUserUseCaseTest {

    @Mock
    private lateinit var cartStorage: CartStorage

    @InjectMocks
    private lateinit var useCase: CleanCartForUserUseCase

    @Test
    fun shouldClearCartWhenItExists() {
        val userId = UUID.randomUUID()
        val cart = Cart(userId).apply {
            addProduct(UUID.randomUUID(), 3)
            addProduct(UUID.randomUUID(), 1)
        }
        whenever(cartStorage.findByUserId(userId)).thenReturn(cart)

        useCase.execute(userId)

        assertTrue(cart.getItems().isEmpty())
        verify(cartStorage).findByUserId(userId)
        verifyNoMoreInteractions(cartStorage)
    }

    @Test
    fun shouldDoNothingWhenCartDoesNotExist() {
        val userId = UUID.randomUUID()
        whenever(cartStorage.findByUserId(userId)).thenReturn(null)

        useCase.execute(userId)

        verify(cartStorage).findByUserId(userId)
        verifyNoMoreInteractions(cartStorage)
    }
}
