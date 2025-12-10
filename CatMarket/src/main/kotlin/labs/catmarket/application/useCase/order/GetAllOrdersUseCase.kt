package labs.catmarket.application.useCase.order

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Order
import labs.catmarket.repository.domainImpl.order.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllOrdersUseCase(
    private val orderRepository: OrderRepository
) : UseCase<Unit, List<Order>>{

    @Transactional(readOnly = true)
    override fun execute(command: Unit) = orderRepository.findAll()
}