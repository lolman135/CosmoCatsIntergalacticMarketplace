package labs.catmarket.featuretoggle.aspect

import labs.catmarket.featuretoggle.FeatureToggleService
import labs.catmarket.featuretoggle.annotation.FeatureToggle
import labs.catmarket.featuretoggle.exception.FeatureToggleNotEnabledException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class FeatureToggleAspect(private val featureToggleService: FeatureToggleService) {

    @Around("@annotation(featureToggle)")
    fun checkFeatureToggleAnnotation(joinPoint: ProceedingJoinPoint, featureToggle: FeatureToggle): Any {
        return checkToggle(joinPoint, featureToggle)
    }

    private fun checkToggle(joinPoint: ProceedingJoinPoint, featureToggle: FeatureToggle): Any{
        val toggle = featureToggle.value
        val enabled = featureToggleService.check(toggle.featureName)
        println(">>> Feature [${toggle.featureName}] enabled=$enabled")

        if(featureToggleService.check(toggle.featureName)){
            return joinPoint.proceed()
        }
        throw FeatureToggleNotEnabledException(toggle.featureName)
    }

}