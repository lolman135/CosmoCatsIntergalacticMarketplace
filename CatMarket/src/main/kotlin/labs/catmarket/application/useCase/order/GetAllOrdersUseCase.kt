package labs.catmarket.application.useCase.order

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.order.Order
import labs.catmarket.domain.order.OrderRepository

class GetAllOrdersUseCase(
    private val orderRepository: OrderRepository
) : UseCase<Unit, List<Order>>{

    override fun execute(command: Unit) = orderRepository.findAll()
}