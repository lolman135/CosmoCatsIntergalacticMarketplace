package labs.catmarket.web

import com.fasterxml.jackson.databind.ObjectMapper
import labs.catmarket.AbstractIT
import labs.catmarket.dto.inbound.CartQuantityInbound
import labs.catmarket.repository.ProductJpaRepository
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.entity.CategoryEntity
import labs.catmarket.repository.entity.ProductEntity
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
    private val productJpaRepository: ProductJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
) : AbstractIT() {

    private val mockUserId = UUID.fromString("d2dc6423-6d5f-46c7-9781-7f2fa2fc1bb9")


    private fun createCategory(): CategoryEntity =
        categoryJpaRepository.save(
            CategoryEntity(
                businessId = UUID.randomUUID(),
                name = "Test Category"
            )
        )

    private fun createProduct(): ProductEntity {
        val category = createCategory()
        val product = ProductEntity(
            businessId = UUID.randomUUID(),
            name = "Test Product",
            description = "test-desc",
            price = 100,
            imageUrl = "http://example.com/img.png",
            category = category
        )
        return productJpaRepository.save(product)
    }

    private fun anySeedProductId(): UUID {
        val created = createProduct()
        val all = productJpaRepository.findAll()
        assertFalse(all.isEmpty(), "Seeded products must exist in JPA repository")
        return created.businessId
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
            .andExpect { status { isNoContent() } }

        mockMvc.get("/api/v1/carts")
            .andExpect {
                status { isOk() }
                jsonPath("$.userId") { value(mockUserId.toString()) }
                jsonPath("$.items") { isArray() }
                jsonPath("$.items.length()") { value(0) }
            }
    }
}
