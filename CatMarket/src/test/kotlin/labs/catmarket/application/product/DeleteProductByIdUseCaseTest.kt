package labs.catmarket.application.product

import labs.catmarket.application.useCase.product.DeleteProductByIdUseCase
import labs.catmarket.repository.domainrepository.product.ProductRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import java.util.UUID

@ExtendWith(MockitoExtension::class)
@DisplayName("Delete Product By Id Use Case Test")
class DeleteProductByIdUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var useCase: DeleteProductByIdUseCase

    @Test
    fun shouldCallRepositoryDeleteById() {
        val id = UUID.randomUUID()

        useCase.execute(id)

        verify(productRepository).deleteById(id)
    }
}
