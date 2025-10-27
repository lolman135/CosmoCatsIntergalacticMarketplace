package labs.catmarket.usecase.order

import labs.catmarket.usecase.useCase.order.GetAllOrdersUseCase
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
class GetAllOrdersUseCaseTest {

    @Mock
    private lateinit var orderRepository: OrderRepository

    @InjectMocks
    private lateinit var useCase: GetAllOrdersUseCase

    @Test
    fun shouldReturnAllOrders() {
        val o1 = Order(
            id = UUID.randomUUID(),
            creationTime = LocalDateTime.now(),
            orderItems = listOf()
        )
        val o2 = Order(
            id = UUID.randomUUID(),
            creationTime = LocalDateTime.now(),
            orderItems = listOf()
        )

        whenever(orderRepository.findAll()).thenReturn(listOf(o1, o2))

        val result = useCase.execute(Unit)

        assertEquals(2, result.size)
        assertTrue(result.containsAll(listOf(o1, o2)))
    }

    @Test
    fun shouldReturnEmptyListWhenNoOrders() {
        whenever(orderRepository.findAll()).thenReturn(emptyList())

        val result = useCase.execute(Unit)

        assertTrue(result.isEmpty())
    }
}
