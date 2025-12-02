package labs.catmarket.featuretoggle.exception

class FeatureToggleNotEnabledException(private val featureName: String) : Exception() {

    override val message: String
        get() = "$featureName is not available"
}