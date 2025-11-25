package labs.catmarket.application.order

import labs.catmarket.application.useCase.order.DeleteOrderByIdUseCase
import labs.catmarket.repository.domainrepository.order.OrderRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import java.util.UUID

@ExtendWith(MockitoExtension::class)
@DisplayName("Delete Order By Id Use Case Test")
class DeleteOrderByIdUseCaseTest {

    @Mock
    private lateinit var orderRepository: OrderRepository

    @InjectMocks
    private lateinit var useCase: DeleteOrderByIdUseCase

    @Test
    fun shouldCallRepositoryDeleteById() {
        val id = UUID.randomUUID()

        useCase.execute(id)

        verify(orderRepository).deleteById(id)
    }
}

