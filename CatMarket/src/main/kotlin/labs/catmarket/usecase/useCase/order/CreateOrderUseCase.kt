package labs.catmarket.usecase.useCase.order

import labs.catmarket.usecase.exception.CartNotFoundException
import labs.catmarket.usecase.exception.DomainNotFoundException
import labs.catmarket.usecase.useCase.UseCase
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
        cart.clear()
        return orderRepository.save(order)
    }
}