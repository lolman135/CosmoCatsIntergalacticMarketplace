package labs.catmarket.web

import com.fasterxml.jackson.databind.ObjectMapper
import labs.catmarket.AbstractIT
import labs.catmarket.dto.inbound.CartQuantityInbound
import labs.catmarket.repository.domainImpl.product.ProductRepository
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put
import java.util.UUID

@DisplayName("Cart Controller Integration Test")
class CartControllerIT @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val productRepository: ProductRepository
) : AbstractIT() {
    private val mockUserId = UUID.fromString("d2dc6423-6d5f-46c7-9781-7f2fa2fc1bb9")

    private fun anySeedProductId(): UUID {
        val all = productRepository.findAll()
        assertFalse(all.isEmpty(), "Seeded products must exist in ProductMockRepository")
        return requireNotNull(all.first().id)
    }

    //POST
    @Test
    fun addProductToCartShouldReturnsNoContent() {
        val productId = anySeedProductId()
        val request = CartQuantityInbound(quantity = 1)

        mockMvc.put("/api/v1/carts/items/$productId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isNoContent() }
        }
    }

    @Test
    fun addProductToCartShouldReturnsBadRequest() {
        val productId = anySeedProductId()
        val request = CartQuantityInbound(quantity = 0)

        mockMvc.put("/api/v1/carts/items/$productId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isBadRequest() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(400) }
            jsonPath("$.detail") { value(containsString("Validation failed")) }
        }
    }

    //GET
    @Test
    fun getCartForUserShouldReturnOkafterAddingItem() {
        val productId = anySeedProductId()
        val request = CartQuantityInbound(quantity = 2)

        mockMvc.put("/api/v1/carts/items/$productId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect { status { isNoContent() } }

        mockMvc.get("/api/v1/carts")
            .andExpect {
                status { isOk() }
                jsonPath("$.userId") { value(mockUserId.toString()) }
                jsonPath("$.items") { isArray() }
                jsonPath("$.items.length()") { value(org.hamcrest.Matchers.greaterThan(0)) }
            }
    }

    //DELETE
    @Test
    fun cleanCartForUserShouldreturnsNoContenThenOk() {
        val productId = anySeedProductId()
        val request = CartQuantityInbound(quantity = 1)
        mockMvc.put("/api/v1/carts/items/$productId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect { status { isNoContent() } }

        mockMvc.delete("/api/v1/carts")
            .andExpect {
                status { isNoContent() }
            }

        mockMvc.get("/api/v1/carts")
            .andExpect {
                status { isOk() }
                jsonPath("$.userId") { value(mockUserId.toString()) }
                jsonPath("$.items") { isArray() }
                jsonPath("$.items.length()") { value(0) }
            }
    }
}
