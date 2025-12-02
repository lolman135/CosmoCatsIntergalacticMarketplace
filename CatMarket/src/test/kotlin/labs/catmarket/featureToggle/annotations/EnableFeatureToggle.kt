package labs.catmarket.featureToggle.annotations

import labs.catmarket.featuretoggle.FeatureToggles

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnableFeatureToggle(val value: FeatureToggles)