package labs.catmarket.application.useCase.order


import labs.catmarket.application.exception.CartNotFoundException
import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.UseCase
import labs.catmarket.common.CartStorage
import labs.catmarket.domain.Order
import labs.catmarket.repository.domainImpl.order.OrderRepository
import labs.catmarket.repository.domainImpl.product.ProductRepository
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val cartStorage: CartStorage
) : UseCase<UUID, Order> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun execute(userId: UUID): Order {
        val cart = cartStorage.findByUserId(userId) ?: throw CartNotFoundException()

        val ordersItems = cart.getItems().map {
            val product = productRepository.findById(it.productId)
                ?: throw DomainNotFoundException("Product", it.productId)

            Order.OrdersItem(
                it.productId,
                it.quantity,
                product.price,
            )
        }.toList()

        val order = Order(id = UUID.randomUUID(), ordersItems = ordersItems)

        if (orderRepository.existsById(order.id!!))
            throw DomainAlreadyExistsException("This Order is already exists")

        cart.clear()
        return orderRepository.save(order)
    }
}