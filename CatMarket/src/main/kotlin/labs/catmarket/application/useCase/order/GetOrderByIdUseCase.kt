package labs.catmarket.application.useCase.order

import labs.catmarket.application.exception.EntityNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.order.Order
import labs.catmarket.domain.order.OrderRepository
import java.util.UUID

class GetOrderByIdUseCase(
    private val orderRepository: OrderRepository
) : UseCase<UUID, Order>{

    override fun execute(id: UUID) = orderRepository.findById(id)
        ?: throw EntityNotFoundException("Order with id=$id not found")
}