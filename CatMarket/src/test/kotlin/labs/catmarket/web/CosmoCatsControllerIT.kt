package labs.catmarket.web

import labs.catmarket.AbstractIT
import labs.catmarket.featureToggle.FeatureToggleExtension
import labs.catmarket.featureToggle.annotations.DisableFeatureToggle
import labs.catmarket.featureToggle.annotations.EnableFeatureToggle
import labs.catmarket.featuretoggle.FeatureToggles
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import kotlin.test.Test


@DisplayName("Cosmo Cats Controller Test")
@ExtendWith(FeatureToggleExtension::class)
class CosmoCatsControllerIT @Autowired constructor(
    private val mockMvc: MockMvc
) : AbstractIT(){

    @Test
    @EnableFeatureToggle(FeatureToggles.COSMO_CATS)
    fun shouldReturnOKStatus() {
        mockMvc.get("/api/v1/cosmo-cats").andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisableFeatureToggle(FeatureToggles.COSMO_CATS)
    fun shouldReturnNotFoundStatus() {
        mockMvc.get("/api/v1/cosmo-cats").andExpect {
            status { isNotFound() }
        }
    }
}