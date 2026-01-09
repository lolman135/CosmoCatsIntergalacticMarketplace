package labs.catmarket.application.useCase.order

import labs.catmarket.application.useCase.UseCase
import labs.catmarket.repository.domainImpl.order.OrderRepository
import org.springframework.security.access.prepost.PreAuthorize
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteOrderByIdUseCase(
    private val orderRepository: OrderRepository
) : UseCase<UUID, Unit>{

    @Transactional
    @PreAuthorize("hasRole('USER')")
    override fun execute(id: UUID) = orderRepository.deleteById(id)
}