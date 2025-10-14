package labs.catmarket.web.controller

import labs.catmarket.application.useCase.cart.AddProductCommand
import labs.catmarket.application.useCase.cart.AddProductToCartUseCase
import labs.catmarket.application.useCase.cart.CleanCartForUserUseCase
import labs.catmarket.application.useCase.cart.GetCartByUserIdUseCase
import labs.catmarket.dto.requet.busines.CartQuantityRequest
import labs.catmarket.dto.response.CartDtoResponse
import labs.catmarket.mapper.CartMapper
import labs.catmarket.mapper.CartMapperHelper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/carts")
class CartController(
    private val cartMapper: CartMapper,
    private val cartMapperHelper: CartMapperHelper,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
    private val cleanCartForUserUseCase: CleanCartForUserUseCase
) {

    //HTTP response: 204
    @PutMapping("/items/{productId}")
    fun addProductToCart(
        @PathVariable productId: UUID,
        @RequestBody request: CartQuantityRequest)
    : ResponseEntity<Unit>{
        val mockUserId = UUID.fromString("d2dc6423-6d5f-46c7-9781-7f2fa2fc1bb9")
        val command = AddProductCommand(mockUserId, productId, request.quantity)
        addProductToCartUseCase.execute(command)
        return ResponseEntity.noContent().build()
    }

    //HTTP response: 200, 404
    @GetMapping
    fun getCartForUser(): ResponseEntity<CartDtoResponse> {
        val mockUserId = UUID.fromString("d2dc6423-6d5f-46c7-9781-7f2fa2fc1bb9")
        val response = CartDtoResponse(
            userId = mockUserId,
            items = getCartByUserIdUseCase.execute(mockUserId).getItems().map {
                cartMapper.toDto(it, cartMapperHelper)
            }.toList()
        )
        return ResponseEntity.ok(response)
    }

    //HTTP response: 204
    @DeleteMapping
    fun cleanCartForUser(): ResponseEntity<Unit> {
        val mockUserId = UUID.fromString("d2dc6423-6d5f-46c7-9781-7f2fa2fc1bb9")
        cleanCartForUserUseCase.execute(mockUserId)
        return ResponseEntity.noContent().build()
    }
}
