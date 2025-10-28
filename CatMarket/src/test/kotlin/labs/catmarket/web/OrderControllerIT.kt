package labs.catmarket.web

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import labs.catmarket.AbstractIT
import labs.catmarket.dto.inbound.CartQuantityInbound
import labs.catmarket.repository.order.OrderRepository
import labs.catmarket.repository.product.ProductRepository
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.util.UUID
import kotlin.test.assertNotNull

@DisplayName("Order Controller Integration Test")
class OrderControllerIT @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
) : AbstractIT() {

    //POST
    @Test
    fun createOrderShouldReturnCreatedWhenCartHasItems() {
        val productId = anySeedProductId()
        addItemToCart(productId, 2)

        mockMvc.post("/api/v1/orders")
            .andExpect {
                status { isCreated() }
                header { exists("Location") }
                content { contentTypeCompatibleWith("application/json") }
                jsonPath("$.id") { exists() }
                jsonPath("$.creationTime") { exists() }
                jsonPath("$.totalCost") { value(greaterThan(0)) }
                jsonPath("$.items") { isArray() }
                jsonPath("$.items.length()") { value(greaterThan(0)) }
            }
    }

    //GET
    @Test
    fun getAllOrdersShouldReturnOkAndList() {
        val productId = anySeedProductId()
        addItemToCart(productId, 1)
        createOrderViaApi()

        mockMvc.get("/api/v1/orders") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentTypeCompatibleWith("application/json") }
            jsonPath("$[0].id") { exists() }
        }
    }

    @Test
    fun getByIdShouldReturnOkWhenExists() {
        val productId = anySeedProductId()
        addItemToCart(productId, 3)
        val id = createOrderViaApi()

        mockMvc.get("/api/v1/orders/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentTypeCompatibleWith("application/json") }
            jsonPath("$.id") { value(id.toString()) }
            jsonPath("$.items.length()") { value(greaterThan(0)) }
        }
    }

    @Test
    fun getByIdShouldReturnNotFoundWhenMissing() {
        mockMvc.get("/api/v1/orders/${UUID.randomUUID()}") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(404) }
            jsonPath("$.detail") { value(containsString("not found")) }
        }
    }

    //DELETE
    @Test
    fun deleteShouldReturnNoContentWhenExists() {
        val productId = anySeedProductId()
        addItemToCart(productId, 1)
        val id = createOrderViaApi()

        mockMvc.delete("/api/v1/orders/$id")
            .andExpect { status { isNoContent() } }

        mockMvc.get("/api/v1/orders/$id")
            .andExpect { status { isNotFound() } }
    }


    //Extra helpers
    private fun anySeedProductId(): UUID {
        val all = productRepository.findAll()
        assertFalse(all.isEmpty(), "Seeded products must exist")
        return requireNotNull(all.first().id)
    }

    private fun addItemToCart(productId: UUID, quantity: Int) {
        val request = CartQuantityInbound(quantity = quantity)
        mockMvc.put("/api/v1/carts/items/$productId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect { status { isNoContent() } }
    }

    private fun createOrderViaApi(): UUID {
        val mvcResult = mockMvc.post("/api/v1/orders")
            .andExpect { status { isCreated() } }
            .andReturn()
        val body = mvcResult.response.contentAsString
        val json: JsonNode = objectMapper.readTree(body)
        val idText = json.get("id").asText()
        val id = UUID.fromString(idText)
        assertNotNull(id)
        return id
    }
}