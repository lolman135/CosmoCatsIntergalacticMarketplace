package labs.catmarket.usecase.useCase.order

import labs.catmarket.usecase.useCase.UseCase
import labs.catmarket.domain.Order
import labs.catmarket.repository.order.OrderRepository
import org.springframework.stereotype.Service

@Service
class GetAllOrdersUseCase(
    private val orderRepository: OrderRepository
) : UseCase<Unit, List<Order>>{

    override fun execute(command: Unit) = orderRepository.findAll()
}