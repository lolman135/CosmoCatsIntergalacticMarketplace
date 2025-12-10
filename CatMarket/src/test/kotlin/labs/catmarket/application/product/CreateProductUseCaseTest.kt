package labs.catmarket.application.product

import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.useCase.product.CreateProductUseCase
import labs.catmarket.application.useCase.product.UpsertProductCommand
import labs.catmarket.domain.Product
import labs.catmarket.repository.domainImpl.category.CategoryRepository
import labs.catmarket.repository.domainImpl.product.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.UUID

@ExtendWith(MockitoExtension::class)
@DisplayName("Create Product Use Case Test")
class CreateProductUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var useCase: CreateProductUseCase

    @Test
    fun shouldCreateProductWhenValid() {
        val categoryId = UUID.randomUUID()
        val command = UpsertProductCommand(
            name = "Cat Toy",
            description = "Fun toy",
            price = 50,
            imageUrl = "http://img",
            categoryId = categoryId
        )

        whenever(productRepository.existsByName(command.name)).thenReturn(false)
        whenever(categoryRepository.existsById(categoryId)).thenReturn(true)
        whenever(productRepository.save(any())).thenAnswer { it.getArgument(0) as Product }

        val result = useCase.execute(command)

        assertNotNull(result.id)
        assertEquals(command.name, result.name)
        assertEquals(command.description, result.description)
        assertEquals(command.price, result.price)
        assertEquals(command.imageUrl, result.imageUrl)
        assertEquals(command.categoryId, result.categoryId)
    }

    @Test
    fun shouldThrowDomainAlreadyExistsExceptionWhenNameTaken() {
        val command = UpsertProductCommand(
            name = "Existing",
            description = "desc",
            price = 10,
            imageUrl = "http://img",
            categoryId = UUID.randomUUID()
        )

        whenever(productRepository.existsByName(command.name)).thenReturn(true)

        assertThrows(DomainAlreadyExistsException::class.java) {
            useCase.execute(command)
        }
    }

    @Test
    fun shouldThrowIllegalArgumentExceptionWhenCategoryInvalid() {
        val categoryId = UUID.randomUUID()
        val command = UpsertProductCommand(
            name = "New",
            description = "desc",
            price = 10,
            imageUrl = "http://img",
            categoryId = categoryId
        )

        whenever(productRepository.existsByName(command.name)).thenReturn(false)
        whenever(categoryRepository.existsById(categoryId)).thenReturn(false)

        assertThrows(IllegalArgumentException::class.java) {
            useCase.execute(command)
        }
    }
}
