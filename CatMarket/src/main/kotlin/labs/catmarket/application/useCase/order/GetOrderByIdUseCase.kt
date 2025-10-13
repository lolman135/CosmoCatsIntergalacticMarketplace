package labs.catmarket.application.useCase.order

import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.order.Order
import labs.catmarket.domain.order.OrderRepository
import java.util.UUID

class GetOrderByIdUseCase(
    private val orderRepository: OrderRepository
) : UseCase<UUID, Order>{

    override fun execute(id: UUID) = orderRepository.findById(id)
        ?: throw DomainNotFoundException("Order", id)
}