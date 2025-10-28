package labs.catmarket.web

import com.fasterxml.jackson.databind.ObjectMapper
import labs.catmarket.AbstractIT
import labs.catmarket.domain.Category
import labs.catmarket.dto.inbound.CategoryDtoInbound
import labs.catmarket.repository.category.CategoryRepository
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

@DisplayName("Category Controller Integration Test")
class CategoryControllerIT @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val categoryRepository: CategoryRepository
) : AbstractIT() {

    @BeforeEach
    fun setUp() {
        categoryRepository.deleteAll()
    }

    //POST
    @Test
    fun saveShouldReturnCreated() {
        val inbound = CategoryDtoInbound(name = "Rocket Vegetables")

        mockMvc.post("/api/v1/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(inbound)
        }.andExpect {
            status { isCreated() }
            header { exists("Location") }
            jsonPath("$.id") { exists() }
            jsonPath("$.name") { value("Rocket Vegetables") }
        }
    }

    @Test
    fun saveShouldReturnBadRequestWhenNameBlank() {
        val inbound = CategoryDtoInbound(name = "   ")

        mockMvc.post("/api/v1/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(inbound)
        }.andExpect {
            status { isBadRequest() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(400) }
            jsonPath("$.detail") { value(containsString("Validation failed")) }
        }
    }

    @Test
    fun saveShouldReturnBadRequestWhenNameTooLong() {
        val inbound = CategoryDtoInbound(name = "a".repeat(101))

        mockMvc.post("/api/v1/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(inbound)
        }.andExpect {
            status { isBadRequest() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(400) }
            jsonPath("$.detail") { value(containsString("Validation failed")) }
        }
    }

    //GET
    @Test
    fun getAllShouldReturnOkAndList() {
        categoryRepository.save(Category(id = UUID.randomUUID(), name = "One"))
        categoryRepository.save(Category(id = UUID.randomUUID(), name = "Two"))

        mockMvc.get("/api/v1/categories") {
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
        val existedId = getExistsCategoryId()

        mockMvc.get("/api/v1/categories/${existedId}") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentTypeCompatibleWith("application/json") }
            jsonPath("$.id") { exists() }
            jsonPath("$.name") { value("Rocket") }
        }
    }

    @Test
    fun getByIdShouldReturnNotFoundWhenMissing() {
        val missingId = UUID.randomUUID()

        mockMvc.get("/api/v1/categories/${missingId}") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(404) }
            jsonPath("$.detail") { value(containsString("not found")) }
        }
    }

    //PUT
    @Test
    fun updateShouldReturnOk() {
        val existedId = getExistsCategoryId()

        val invalid = CategoryDtoInbound(name = "Asteroid")

        mockMvc.put("/api/v1/categories/${existedId}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalid)
        }.andExpect {
            status { isOk() }
            content { contentTypeCompatibleWith("application/json") }
            jsonPath("$.id") { exists() }
            jsonPath("$.name") { value("Asteroid") }
        }
    }

    @Test
    fun updateShouldReturnBadRequestWhenBodyInvalid() {
        val invalid = CategoryDtoInbound(name = "")

        mockMvc.put("/api/v1/categories/${UUID.randomUUID()}") {
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
    fun updateShouldReturnBadRequestWhenNameToLong() {
        val invalid = CategoryDtoInbound(name = "a".repeat(101))

        mockMvc.put("/api/v1/categories/${UUID.randomUUID()}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalid)
        }.andExpect {
            status { isBadRequest() }
            content { contentTypeCompatibleWith("application/problem+json") }
            jsonPath("$.status") { value(400) }
            jsonPath("$.detail") { value(containsString("Validation failed")) }
        }
    }

    //DELETE
    @Test
    fun deleteShouldReturnNoContentWhenExists() {
        val existedId = getExistsCategoryId()

        mockMvc.delete("/api/v1/categories/${existedId}")
            .andExpect {
                status { isNoContent() }
            }

        mockMvc.get("/api/v1/categories/${existedId}") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    //Extra helpers
    private fun getExistsCategoryId(): UUID {
        categoryRepository.save(Category(id = UUID.randomUUID(), name = "Rocket"))
        return categoryRepository.findAll().first().id!!
    }

}
