package labs.catmarket.application.product

import labs.catmarket.application.exception.ProjectionNotFoundException
import labs.catmarket.application.useCase.product.GetProductProjectionByNameUseCase
import labs.catmarket.repository.domainImpl.product.ProductRepository
import labs.catmarket.repository.projection.ProductDetailsProjection
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
@DisplayName("Get Product Projection By Name Use Case Test")
class GetProductProjectionByNameUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var useCase: GetProductProjectionByNameUseCase

    private val projection = object : ProductDetailsProjection {
        override fun getName() = "Shlyapkus"
        override fun getDescription() = "Epic test hat"
        override fun getPrice() = 123
    }

    @Test
    fun shouldReturnProjectionDtoWhenProjectionExists() {
        whenever(productRepository.findProjectionByName("Shlyapkus"))
            .thenReturn(projection)

        val result = useCase.execute("Shlyapkus")

        assertEquals("Shlyapkus", result.name)
        assertEquals("Epic test hat", result.description)
        assertEquals(123, result.price)

        verify(productRepository).findProjectionByName("Shlyapkus")
        verifyNoMoreInteractions(productRepository)
    }

    @Test
    fun shouldThrowExceptionWhenProjectionNotFound() {
        whenever(productRepository.findProjectionByName("Unknown"))
            .thenReturn(null)

        assertThrows<ProjectionNotFoundException> {
            useCase.execute("Unknown")
        }

        verify(productRepository).findProjectionByName("Unknown")
        verifyNoMoreInteractions(productRepository)
    }
}
