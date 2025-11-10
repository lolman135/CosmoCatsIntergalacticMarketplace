package labs.catmarket.featureToggle

import labs.catmarket.featureToggle.annotations.DisableFeatureToggle
import labs.catmarket.featureToggle.annotations.EnableFeatureToggle
import labs.catmarket.featuretoggle.FeatureToggleService
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class FeatureToggleExtension:  BeforeEachCallback, AfterEachCallback{

    override fun beforeEach(context: ExtensionContext) {
        context.testMethod.ifPresent { method ->
            val featureToggleService = getFeatureToggleService(context)

            if(method.isAnnotationPresent(EnableFeatureToggle::class.java)) {
                val enableFeatureToggle = method.getAnnotation(EnableFeatureToggle::class.java)
                featureToggleService.enable(enableFeatureToggle.value.featureName)
            } else if (method.isAnnotationPresent(DisableFeatureToggle::class.java)){
                val disableFeatureToggle = method.getAnnotation(DisableFeatureToggle::class.java)
                featureToggleService.disable(disableFeatureToggle.value.featureName)
            }
        }
    }

    override fun afterEach(context: ExtensionContext) {
        context.testMethod.ifPresent { method ->
            var featureName: String? = null

            if(method.isAnnotationPresent(EnableFeatureToggle::class.java)){
                val enableFeatureToggle = method.getAnnotation(EnableFeatureToggle::class.java)
                featureName = enableFeatureToggle.value.featureName
            } else if(method.isAnnotationPresent(DisableFeatureToggle::class.java)) {
                val disableFeatureToggle = method.getAnnotation(DisableFeatureToggle::class.java)
                featureName = disableFeatureToggle.value.featureName
            }
            if (featureName != null){
                val featureToggleService = getFeatureToggleService(context)
                if(getFeatureNamePropertyAsBoolean(context, featureName)){
                    featureToggleService.enable(featureName)
                } else {
                    featureToggleService.disable(featureName)
                }
            }
        }
    }

    private fun getFeatureNamePropertyAsBoolean(context: ExtensionContext, featureName: String): Boolean {
        val environment = SpringExtension.getApplicationContext(context).getEnvironment()
        return environment.getProperty("application.feature.toggles.$featureName", Boolean::class.java, false)
    }

    private fun getFeatureToggleService(context: ExtensionContext): FeatureToggleService {
        return SpringExtension.getApplicationContext(context).getBean(FeatureToggleService::class.java)
    }
}