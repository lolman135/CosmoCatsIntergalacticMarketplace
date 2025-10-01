package labs.catmarket.application.useCase.order

import labs.catmarket.application.exception.EntityNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.domain.cart.CartStorage
import labs.catmarket.domain.order.Order
import labs.catmarket.domain.order.OrderRepository
import labs.catmarket.domain.product.ProductRepository
import java.util.UUID

class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val cartStorage: CartStorage
) : UseCase<UUID, Order> {

    override fun execute(userId: UUID): Order {
        val cart = cartStorage.findByUserId(userId) ?: throw EntityNotFoundException("Cart for this user not found")

        val orderItems = cart.getItems().map {
            val product = productRepository.findById(it.productId)
                ?: throw EntityNotFoundException("Product with id=${it.productId} not found")

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