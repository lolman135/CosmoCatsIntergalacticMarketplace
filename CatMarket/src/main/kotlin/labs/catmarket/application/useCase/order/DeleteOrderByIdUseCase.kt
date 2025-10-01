package labs.catmarket.application.useCase.order

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.order.OrderRepository
import java.util.UUID

class DeleteOrderByIdUseCase(
    private val orderRepository: OrderRepository
) : UseCase<UUID, Unit>{

    override fun execute(id: UUID) = orderRepository.deleteById(id)
}