package labs.catmarket.usecase.product

import labs.catmarket.usecase.useCase.product.DeleteProductByIdUseCase
import labs.catmarket.repository.product.ProductRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import java.util.UUID

@ExtendWith(MockitoExtension::class)
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
