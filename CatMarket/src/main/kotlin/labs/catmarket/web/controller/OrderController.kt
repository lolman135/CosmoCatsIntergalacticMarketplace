package labs.catmarket.web.controller

import labs.catmarket.application.useCase.order.CreateOrderUseCase
import labs.catmarket.application.useCase.order.DeleteOrderByIdUseCase
import labs.catmarket.application.useCase.order.GetAllOrdersUseCase
import labs.catmarket.application.useCase.order.GetOrderByIdUseCase
import labs.catmarket.dto.outbound.OrderDtoOutbound
import labs.catmarket.mapper.OrderMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val deleteOrderByIdUseCase: DeleteOrderByIdUseCase,
    private val orderMapper: OrderMapper
) {

    //Little bit about POST endpoint. To create an order, we need to get a cart by id of user.
    //Due to the inability to register new users, interaction with them will be simulated through their IDs.
    //So for now, in the POST request, I'll simply send the ID of a theoretically existing user as mockUserId.
    //In the future i'm going to add an ability to register users and store their info in DB and security context,
    //and then, the current user's ID will be taken from the context (token)
    @PostMapping
    fun createOrder(): ResponseEntity<OrderDtoOutbound> {
        val mockUserId = UUID.fromString("d2dc6423-6d5f-46c7-9781-7f2fa2fc1bb9")
        val order = createOrderUseCase.execute(mockUserId)
        val location = URI.create("/api/v1/orders/${order.id}")
        return ResponseEntity.created(location).body(orderMapper.toDto(order))
    }

    @GetMapping
    fun getAllOrders() = ResponseEntity.ok(getAllOrdersUseCase.execute(Unit).map {
        orderMapper.toDto(it)
    })

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: UUID) = ResponseEntity.ok(orderMapper.toDto(getOrderByIdUseCase.execute(id)))

    @DeleteMapping("/{id}")
    fun deleteOrderById(@PathVariable id: UUID): ResponseEntity<Void> {
        deleteOrderByIdUseCase.execute(id)
        return ResponseEntity.noContent().build()
    }
}