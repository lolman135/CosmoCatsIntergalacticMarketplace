package labs.catmarket.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component
import java.net.MalformedURLException
import java.net.URL

@Component
class UrlValidator : ConstraintValidator<Url, String>{

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?
    ): Boolean {
        if (value.isNullOrBlank()) return false

        return try {
            val url = URL(value)
            !url.protocol.isNullOrBlank() && !url.host.isNullOrBlank()
        } catch (e: MalformedURLException) {
            false
        }
    }
}