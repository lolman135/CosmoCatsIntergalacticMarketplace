// Kotlin
package labs.catmarket.web

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import labs.catmarket.AbstractIT
import labs.catmarket.domain.Category
import labs.catmarket.dto.inbound.ProductDtoInbound
import labs.catmarket.repository.domainrepository.category.CategoryRepository
import labs.catmarket.repository.domainrepository.product.ProductRepository
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.BeforeEach
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

@DisplayName("Product Controller Integration Test")
class ProductControllerIT @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : AbstractIT() {

    @BeforeEach
    fun setUp() {
        productRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    //POST
    @Test
    fun postShouldReturnCreatedWhenValid() {
        val categoryId = seedCategory("Veggies")
        val inbound = validProduct(categoryId)

        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(inbound)
        }.andExpect {
            status { isCreated() }
            header { exists("Location") }
            jsonPath("$.id") { exists() }
            jsonPath("$.name") { value(inbound.name) }
            jsonPath("$.description") { value(inbound.description) }
            jsonPath("$.price") { value(inbound.price) }
            jsonPath("$.imageUrl") { value(inbound.imageUrl) }
            jsonPath("$.category") { value("Veggies") }
        }
    }

    @Test
    fun postShouldReturnBadRequestWhenNameBlank() {
        val categoryId = seedCategory("Veggies")
        val invalid = ProductDtoInbound(
            name = "   ",
            description = "desc",
            price = 10,
            imageUrl = "http://image.img",
            categoryId = categoryId
        )

        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalid)
        }.andExpect {
            status { isBadRequest() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(400) }
            jsonPath("$.detail") { value(containsString("Validation failed")) }
        }
    }

    @Test
    fun postShouldReturnBadRequestWhenPriceTooLow() {
        val categoryId = seedCategory("Veggies")
        val invalid = ProductDtoInbound(
            name = "Ok",
            description = "desc",
            price = 0,
            imageUrl = "http://image.img",
            categoryId = categoryId
        )

        mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalid)
        }.andExpect {
            status { isBadRequest() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(400) }
            jsonPath("$.detail") { value(containsString("Validation failed")) }
        }
    }

    // GET

    @Test
    fun getAllShouldReturnOkAndList() {
        val categoryId = seedCategory("Veggies")
        createProductViaApi(validProduct(categoryId))
        createProductViaApi(validProduct(categoryId).copy(name = "Another"))

        mockMvc.get("/api/v1/products") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentTypeCompatibleWith("application/json") }
            jsonPath("$[0].id") { exists() }
            jsonPath("$[1].id") { exists() }
        }
    }

    @Test
    fun getByIdShouldReturnOkWhenExists() {
        val categoryId = seedCategory("Veggies")
        val id = createProductViaApi(validProduct(categoryId))

        mockMvc.get("/api/v1/products/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(id.toString()) }
            jsonPath("$.category") { value("Veggies") }
        }
    }

    @Test
    fun getByIdShouldReturnNotFoundWhenMissing() {
        mockMvc.get("/api/v1/products/${UUID.randomUUID()}") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(404) }
            jsonPath("$.detail") { value(containsString("not found")) }
        }
    }

    // PUT

    @Test
    fun putShouldReturnOkWhenValid() {
        val categoryId = seedCategory("Veggies")
        val id = createProductViaApi(validProduct(categoryId))

        val updated = ProductDtoInbound(
            name = "Updated",
            description = "Updated desc",
            price = 777,
            imageUrl = "http://img.updated",
            categoryId = categoryId
        )

        mockMvc.put("/api/v1/products/$id") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updated)
        }.andExpect {
            status { isOk() }
            content { contentTypeCompatibleWith("application/json") }
            jsonPath("$.id") { value(id.toString()) }
            jsonPath("$.name") { value("Updated") }
            jsonPath("$.description") { value("Updated desc") }
            jsonPath("$.price") { value(777) }
            jsonPath("$.imageUrl") { value("http://img.updated") }
            jsonPath("$.category") { value("Veggies") }
        }
    }

    @Test
    fun putShouldReturnBadRequestWhenNameTooLong() {
        val categoryId = seedCategory("Veggies")
        val id = createProductViaApi(validProduct(categoryId))

        val invalid = ProductDtoInbound(
            name = "a".repeat(101),
            description = "desc",
            price = 10,
            imageUrl = "http://image.img",
            categoryId = categoryId
        )

        mockMvc.put("/api/v1/products/$id") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalid)
        }.andExpect {
            status { isBadRequest() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(400) }
            jsonPath("$.detail") { value(containsString("Validation failed")) }
        }
    }

    @Test
    fun putShouldReturnBadRequestWhenImageUrlInvalid() {
        val categoryId = seedCategory("Veggies")
        val id = createProductViaApi(validProduct(categoryId))

        val invalid = ProductDtoInbound(
            name = "Ok",
            description = "desc",
            price = 10,
            imageUrl = "not-a-url",
            categoryId = categoryId
        )

        mockMvc.put("/api/v1/products/$id") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalid)
        }.andExpect {
            status { isBadRequest() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(400) }
            jsonPath("$.detail") { value(containsString("Validation failed")) }
        }
    }

    // DELETE

    @Test
    fun deleteShouldReturnNoContentWhenExists() {
        val categoryId = seedCategory("Veggies")
        val id = createProductViaApi(validProduct(categoryId))

        mockMvc.delete("/api/v1/products/$id")
            .andExpect { status { isNoContent() } }

        mockMvc.get("/api/v1/products/$id")
            .andExpect { status { isNotFound() } }
    }

    //Extra helpers

    private fun validProduct(categoryId: UUID) = ProductDtoInbound(
        name = "Cucumber-9000 Pro Max",
        description = "Super cucumber with built-in rocket-engine",
        price = 1_000_000,
        imageUrl = "http://image.img",
        categoryId = categoryId
    )

    private fun seedCategory(name: String): UUID {
        val id = UUID.randomUUID()
        categoryRepository.save(Category(id = id, name = name))
        return id
    }

    private fun createProductViaApi(dto: ProductDtoInbound): UUID {
        val mvcResult = mockMvc.post("/api/v1/products") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isCreated() }
        }.andReturn()

        val body = mvcResult.response.contentAsString
        val json: JsonNode = objectMapper.readTree(body)
        val idText = json.get("id").asText()
        val id = UUID.fromString(idText)
        assertNotNull(id)
        return id
    }
}