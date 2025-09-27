package labs.catmarket.infrastructure.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UrlValidator::class])
annotation class Url(
    val message: String = "Invalid URL",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
