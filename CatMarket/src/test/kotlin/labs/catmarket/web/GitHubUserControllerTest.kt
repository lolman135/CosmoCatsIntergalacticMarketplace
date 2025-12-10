package labs.catmarket.web

import com.github.tomakehurst.wiremock.client.WireMock.*
import labs.catmarket.AbstractIT
import labs.catmarket.CatMarketApplication
import labs.catmarket.common.WireMockContainer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GitHubUserControllerTest() : AbstractIT(){

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeAll
    fun setupStub() {
        configureFor("localhost", container.firstMappedPort)

        stubFor(
            get(urlEqualTo("/user"))
                .willReturn(
                    okJson(
                        """
                        {
                          "login": "catJack",
                          "id": 103,
                          "name": "JACK",
                          "public_repos": -5
                        }
                        """.trimIndent()
                    )
                )
        )
    }

    @Test
    fun getMeShouldReturnGitHubUser() {
        mockMvc.get("/api/v1/github-user/me") {
            header(HttpHeaders.AUTHORIZATION, "Bearer dummy-token")
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.login") { value("catJack") }
            jsonPath("$.name") { value("JACK") }
        }
    }

}