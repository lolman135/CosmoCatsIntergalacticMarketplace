package labs.catmarket.usecase.useCase.order

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.repository.order.OrderRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class DeleteOrderByIdUseCase(
    private val orderRepository: OrderRepository
) : UseCase<UUID, Unit>{

    override fun execute(id: UUID) = orderRepository.deleteById(id)
}