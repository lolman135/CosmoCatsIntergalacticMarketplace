package labs.catmarket.featureToggle.annotations

import labs.catmarket.featuretoggle.FeatureToggles

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DisableFeatureToggle(val value: FeatureToggles)
