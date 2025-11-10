package labs.catmarket.featuretoggle.annotation

import labs.catmarket.featuretoggle.FeatureToggles

@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureToggle(val value: FeatureToggles)
