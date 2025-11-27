package labs.catmarket.mapper

import labs.catmarket.application.useCase.product.GetProductByIdUseCase
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderMapperHelper(private val getProductByIdUseCase: GetProductByIdUseCase) {

    fun getProductNameFromId(id: UUID) = getProductByIdUseCase.execute(id).name
}