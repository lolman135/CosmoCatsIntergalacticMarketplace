package labs.catmarket.application.product

import labs.catmarket.application.useCase.product.GetAllProductsUseCase
import labs.catmarket.domain.Product
import labs.catmarket.repository.domainrepository.product.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.UUID

@ExtendWith(MockitoExtension::class)
@DisplayName("Get All Products Use Case Test")
class GetAllProductsUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var useCase: GetAllProductsUseCase

    @Test
    fun shouldReturnAllProducts() {
        val p1 = Product(UUID.randomUUID(), "A", "d", 1, "u", UUID.randomUUID())
        val p2 = Product(UUID.randomUUID(), "B", "d2", 2, "u2", UUID.randomUUID())

        whenever(productRepository.findAll()).thenReturn(listOf(p1, p2))

        val result = useCase.execute(Unit)

        assertEquals(2, result.size)
        assertTrue(result.containsAll(listOf(p1, p2)))
    }
}
