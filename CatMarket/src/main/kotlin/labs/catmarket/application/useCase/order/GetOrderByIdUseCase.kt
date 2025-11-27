package labs.catmarket.application.useCase.order

import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Order
import labs.catmarket.repository.domainImpl.order.OrderRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class GetOrderByIdUseCase(
    private val orderRepository: OrderRepository
) : UseCase<UUID, Order>{

    override fun execute(id: UUID) = orderRepository.findById(id)
        ?: throw DomainNotFoundException("Order", id)
}