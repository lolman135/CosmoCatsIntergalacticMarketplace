package labs.catmarket.application.useCase.order

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Order
import labs.catmarket.repository.domainrepository.order.OrderRepository
import org.springframework.stereotype.Service

@Service
class GetAllOrdersUseCase(
    private val orderRepository: OrderRepository
) : UseCase<Unit, List<Order>>{

    override fun execute(command: Unit) = orderRepository.findAll()
}