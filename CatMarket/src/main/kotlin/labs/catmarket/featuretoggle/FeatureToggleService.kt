package labs.catmarket.featuretoggle

import labs.catmarket.config.FeatureToggleProperties
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Service
class FeatureToggleService(featureToggleProperties: FeatureToggleProperties) {

    private val featureToggles: ConcurrentMap<String, Boolean> = ConcurrentHashMap(featureToggleProperties.toggles)

    fun check(featureName: String): Boolean {
        return featureToggles.getOrDefault(featureName, false)
    }

    fun enable(featureName: String){
        featureToggles[featureName] = true
    }

    fun disable(featureName: String){
        featureToggles[featureName] = false
    }
}