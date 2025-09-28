package labs.catmarket.infrastructure.controller

import labs.catmarket.application.useCase.cart.AddProductToCartUseCase
import labs.catmarket.application.useCase.cart.CleanCartForUserUseCase
import labs.catmarket.application.useCase.cart.GetCartByUserIdUseCase
import labs.catmarket.infrastructure.dto.requet.busines.CartDtoRequest
import labs.catmarket.infrastructure.dto.response.CartDtoResponse
import labs.catmarket.infrastructure.dto.response.CartItemDtoResponse
import labs.catmarket.infrastructure.mapper.CartWebMapper
import labs.catmarket.infrastructure.mapper.CartWebMapperHelper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/v1/carts")
class CartController(
    private val cartWebMapper: CartWebMapper,
    private val cartWebMapperHelper: CartWebMapperHelper,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
    private val cleanCartForUserUseCase: CleanCartForUserUseCase
) {

    @PostMapping
    fun addProductToCart(@RequestBody request: CartDtoRequest): ResponseEntity<Unit>{
        val command = cartWebMapper.toCommand(request)
        addProductToCartUseCase.execute(command)
        val location = URI.create("/api/v1/carts")
        return ResponseEntity.created(location).build()
    }

    @GetMapping("/{userId}")
    fun getCartForUser(@PathVariable userId: UUID): ResponseEntity<CartDtoResponse> {
        val response = CartDtoResponse(
            userId = userId,
            items = getCartByUserIdUseCase.execute(userId).getItems().map {
                cartWebMapper.toDto(it, cartWebMapperHelper)
            }.toList()
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{userId}")
    fun cleanCartForUser(@PathVariable userId: UUID): ResponseEntity<Unit> {
        cleanCartForUserUseCase.execute(userId)
        return ResponseEntity.noContent().build()
    }
}
