package labs.catmarket.dto.inbound

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoryDtoInbound(

    @field:NotBlank(message = "Product name must not be blank")
    @field:Size(max = 40, message = "Product name must be at most 100 characters")
    val name: String
)