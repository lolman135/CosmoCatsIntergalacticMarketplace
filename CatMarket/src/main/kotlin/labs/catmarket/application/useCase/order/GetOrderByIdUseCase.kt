package labs.catmarket.application.useCase.order

import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.Order
import labs.catmarket.repository.domainImpl.order.OrderRepository
import org.springframework.security.access.prepost.PreAuthorize
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetOrderByIdUseCase(
    private val orderRepository: OrderRepository
) : UseCase<UUID, Order>{

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('USER')")
    override fun execute(id: UUID) = orderRepository.findById(id)
        ?: throw DomainNotFoundException("Order", id)
}