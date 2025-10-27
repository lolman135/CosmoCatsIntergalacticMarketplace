package labs.catmarket.application.cart

import labs.catmarket.application.exception.CartNotFoundException
import labs.catmarket.application.useCase.cart.GetCartByUserIdUseCase
import labs.catmarket.common.CartStorage
import labs.catmarket.domain.Cart
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
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
@DisplayName("Get Cart By User Id Use Case Test")
class GetCartByUserIdUseCaseTest {

    @Mock
    private lateinit var cartStorage: CartStorage

    @InjectMocks
    private lateinit var useCase: GetCartByUserIdUseCase

    @Test
    fun shouldReturnCartWhenItExists() {
        val userId = UUID.randomUUID()
        val cart = Cart(userId)
        whenever(cartStorage.findByUserId(userId)).thenReturn(cart)

        val result = useCase.execute(userId)

        assertSame(cart, result)
        verify(cartStorage).findByUserId(userId)
        verifyNoMoreInteractions(cartStorage)
    }

    @Test
    fun shouldThrowCartNotFoundWhenAbsent() {
        val userId = UUID.randomUUID()
        whenever(cartStorage.findByUserId(userId)).thenReturn(null)

        val ex = assertThrows(CartNotFoundException::class.java) {
            useCase.execute(userId)
        }
        assertEquals("Cart for this user not found", ex.message)

        verify(cartStorage).findByUserId(userId)
        verifyNoMoreInteractions(cartStorage)
    }
}
