package labs.catmarket.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("application.feature")
class FeatureToggleProperties {

    val toggles: MutableMap<String, Boolean> = mutableMapOf()

    fun check(featureToggle: String) = toggles.getOrDefault(featureToggle, false)
}