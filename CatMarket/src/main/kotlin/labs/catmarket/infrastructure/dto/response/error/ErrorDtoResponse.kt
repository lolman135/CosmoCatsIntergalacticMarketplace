package labs.catmarket.infrastructure.dto.response.error

import java.time.LocalDateTime

data class ErrorDtoResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String? = null,
    val errors: Map<String, String>? = null
)
