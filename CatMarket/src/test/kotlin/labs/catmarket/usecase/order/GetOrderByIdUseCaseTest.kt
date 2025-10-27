package labs.catmarket.usecase.order

import labs.catmarket.usecase.exception.DomainNotFoundException
import labs.catmarket.usecase.useCase.order.GetOrderByIdUseCase
import labs.catmarket.domain.Order
import labs.catmarket.repository.order.OrderRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class GetOrderByIdUseCaseTest {

    @Mock
    private lateinit var orderRepository: OrderRepository

    @InjectMocks
    private lateinit var useCase: GetOrderByIdUseCase

    @Test
    fun shouldReturnOrderWhenFound() {
        val id = UUID.randomUUID()
        val order = Order(
            id = id,
            creationTime = LocalDateTime.now(),
            orderItems = listOf()
        )

        whenever(orderRepository.findById(id)).thenReturn(order)

        val result = useCase.execute(id)

        assertEquals(order, result)
    }

    @Test
    fun shouldThrowDomainNotFoundExceptionWhenOrderNotFound() {
        val id = UUID.randomUUID()
        whenever(orderRepository.findById(id)).thenReturn(null)

        assertThrows(DomainNotFoundException::class.java) {
            useCase.execute(id)
        }
    }
}
