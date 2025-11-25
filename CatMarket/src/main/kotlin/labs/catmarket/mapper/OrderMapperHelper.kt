package labs.catmarket.mapper

import labs.catmarket.application.useCase.order.GetOrderByIdUseCase
import labs.catmarket.application.useCase.product.GetProductByIdUseCase
import labs.catmarket.repository.domainrepository.order.OrderRepository
import labs.catmarket.repository.domainrepository.product.ProductRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderMapperHelper(private val getProductByIdUseCase: GetProductByIdUseCase) {

    fun getProductNameFromId(id: UUID) = getProductByIdUseCase.execute(id).name
}