package labs.catmarket.mapper

import labs.catmarket.usecase.useCase.product.GetProductByIdUseCase
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CartMapperHelper(
    private val getProductByIdUseCase: GetProductByIdUseCase
) {
    fun getProductNameById(id: UUID) = getProductByIdUseCase.execute(id).name
    fun getProductPriceById(id: UUID) = getProductByIdUseCase.execute(id).price
}