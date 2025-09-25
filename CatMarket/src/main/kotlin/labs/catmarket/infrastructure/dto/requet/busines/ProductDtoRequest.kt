package labs.catmarket.infrastructure.dto.requet.busines

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class ProductDtoRequest(

    @field:NotBlank(message = "Product name must not be blank")
    @field:Size(max = 100, message = "Product name must be at most 100 characters")
    val name: String,

    @field:Min(value = 1, message = "Price must be greater than 0")
    val price: Int,

    @field:NotBlank(message = "Image URL must not be blank")
    val imageUrl: String,

    val categoryId: UUID
)
