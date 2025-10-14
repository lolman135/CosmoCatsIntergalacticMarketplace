package labs.catmarket.dto.requet.busines

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import labs.catmarket.validation.Url
import java.util.UUID

data class ProductDtoRequest(

    @field:NotBlank(message = "Product name must not be blank")
    @field:Size(max = 100, message = "Product name must be at most 100 characters")
    val name: String,

    @field:NotBlank(message = "Product description must not be blank")
    @field:Size(max = 300, message = "Product description must be at most 300 characters")
    val description: String,

    @field:Min(value = 1, message = "Price must be greater than 0")
    val price: Int,

    @field:Url
    val imageUrl: String,

    val categoryId: UUID
)
