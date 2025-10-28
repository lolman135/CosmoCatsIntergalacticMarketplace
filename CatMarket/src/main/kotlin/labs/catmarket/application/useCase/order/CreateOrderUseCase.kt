package labs.catmarket.application.useCase.order

import labs.catmarket.application.exception.CartNotFoundException
import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.common.CartStorage
import labs.catmarket.domain.Order
import labs.catmarket.repository.order.OrderRepository
import labs.catmarket.repository.product.ProductRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val cartStorage: CartStorage
) : UseCase<UUID, Order> {

    override fun execute(userId: UUID): Order {
        val cart = cartStorage.findByUserId(userId) ?: throw CartNotFoundException()

        val orderItems = cart.getItems().map {
            val product = productRepository.findById(it.productId)
                ?: throw DomainNotFoundException("Product", it.productId)

            Order.OrderItem(
                it.productId,
                it.quantity,
                product.price,
                product.name
            )
        }.toList()

        val order = Order(id = UUID.randomUUID(), orderItems = orderItems)

        if (orderRepository.existsById(order.id!!))
            throw DomainAlreadyExistsException("This Order is already exists")

        cart.clear()
        return orderRepository.save(order)
    }
}