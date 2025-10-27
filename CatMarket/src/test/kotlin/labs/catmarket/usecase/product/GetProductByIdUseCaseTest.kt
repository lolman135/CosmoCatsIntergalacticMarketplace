package labs.catmarket.usecase.product

import labs.catmarket.usecase.exception.DomainNotFoundException
import labs.catmarket.usecase.useCase.product.GetProductByIdUseCase
import labs.catmarket.domain.Product
import labs.catmarket.repository.product.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class GetProductByIdUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var useCase: GetProductByIdUseCase

    @Test
    fun shouldReturnProductWhenFound() {
        val id = UUID.randomUUID()
        val product = Product(id, "Name", "desc", 5, "url", UUID.randomUUID())

        whenever(productRepository.findById(id)).thenReturn(product)

        val result = useCase.execute(id)

        assertEquals(product, result)
    }

    @Test
    fun shouldThrowDomainNotFoundExceptionWhenNotFound() {
        val id = UUID.randomUUID()
        whenever(productRepository.findById(id)).thenReturn(null)

        assertThrows(DomainNotFoundException::class.java) {
            useCase.execute(id)
        }
    }
}
