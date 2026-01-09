package labs.catmarket.web

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import labs.catmarket.AbstractIT
import labs.catmarket.application.useCase.cart.CleanCartForUserUseCase
import labs.catmarket.common.CartStorage
import labs.catmarket.domain.Status
import labs.catmarket.dto.inbound.CartQuantityInbound
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.OrderJpaRepository
import labs.catmarket.repository.ProductJpaRepository
import labs.catmarket.repository.entity.CategoryEntity
import labs.catmarket.repository.entity.ProductEntity
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertNotNull

@DisplayName("Order Controller Integration Test")
class OrderControllerIT @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val productJpaRepository: ProductJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
    private val orderJpaRepository: OrderJpaRepository,
    private val cartStorage: CartStorage
) : AbstractIT() {

    //Extra helpers

    private fun createCategory(): CategoryEntity =
        categoryJpaRepository.save(
            CategoryEntity(
                businessId = UUID.randomUUID(),
                name = "Seed Category"
            )
        )

    private fun createProduct(): ProductEntity {
        val category = createCategory()
        return productJpaRepository.save(
            ProductEntity(
                businessId = UUID.randomUUID(),
                name = "Test Product",
                description = "desc",
                price = 120,
                imageUrl = "img",
                category = category
            )
        )
    }

    private fun anySeedProductId(): UUID {
        val product = createProduct()
        val all = productJpaRepository.findAll()
        assertFalse(all.isEmpty(), "At least one product must exist")
        return product.businessId
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

        val json: JsonNode = objectMapper.readTree(mvcResult.response.contentAsString)
        val id = UUID.fromString(json.get("id").asText())
        assertNotNull(id)
        return id
    }

    @BeforeEach
    fun cleanCart(){
        cartStorage.clearAll()
    }

    //POST
    @Test
    @WithMockUser
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
                jsonPath("$.items") { isArray() }
                jsonPath("$.items.length()") { value(greaterThan(0)) }
            }
    }

    //GET
    @Test
    @WithMockUser
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
    @WithMockUser
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
    @WithMockUser
    fun getByIdShouldReturnNotFoundWhenMissing() {
        mockMvc.get("/api/v1/orders/${UUID.randomUUID()}") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
            jsonPath("$.status") { value(404) }
            jsonPath("$.detail") { exists() }
        }
    }

    //DELETE
    @Test
    @WithMockUser
    fun deleteShouldReturnNoContentWhenExists() {
        val productId = anySeedProductId()
        addItemToCart(productId, 1)
        val id = createOrderViaApi()

        mockMvc.delete("/api/v1/orders/$id")
            .andExpect { status { isNoContent() } }

        mockMvc.get("/api/v1/orders/$id")
            .andExpect { status { isNotFound() } }
    }
}
