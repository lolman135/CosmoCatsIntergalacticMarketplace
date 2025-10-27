package labs.catmarket.usecase.product

import labs.catmarket.usecase.exception.DomainAlreadyExistsException
import labs.catmarket.usecase.exception.DomainNotFoundException
import labs.catmarket.usecase.useCase.product.UpdateProductByIdUseCase
import labs.catmarket.usecase.useCase.product.UpsertProductCommand
import labs.catmarket.domain.Product
import labs.catmarket.repository.category.CategoryRepository
import labs.catmarket.repository.product.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class UpdateProductByIdUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var useCase: UpdateProductByIdUseCase

    @Test
    fun shouldUpdateProductWhenValid() {
        val id = UUID.randomUUID()
        val existing = Product(id, "Old", "old", 1, "u", UUID.randomUUID())
        val newCategory = UUID.randomUUID()
        val command = UpsertProductCommand("New", "newDesc", 10, "newUrl", newCategory)

        whenever(productRepository.findById(id)).thenReturn(existing)
        whenever(productRepository.existsByName(command.name)).thenReturn(false)
        whenever(categoryRepository.existsById(newCategory)).thenReturn(true)
        whenever(productRepository.save(any())).thenAnswer { it.getArgument(0) as Product }

        val result = useCase.execute(Pair(id, command))

        assertEquals(id, result.id)
        assertEquals("New", result.name)
        assertEquals("newDesc", result.description)
        assertEquals(10, result.price)
        assertEquals("newUrl", result.imageUrl)
        assertEquals(newCategory, result.categoryId)
    }

    @Test
    fun shouldThrowDomainNotFoundExceptionWhenProductMissing() {
        val id = UUID.randomUUID()
        val command = UpsertProductCommand("N", "d", 1, "u", UUID.randomUUID())

        whenever(productRepository.findById(id)).thenReturn(null)

        assertThrows(DomainNotFoundException::class.java) {
            useCase.execute(Pair(id, command))
        }
    }

    @Test
    fun shouldThrowDomainAlreadyExistsExceptionWhenNameTakenByOther() {
        val id = UUID.randomUUID()
        val existing = Product(id, "Old", "old", 1, "u", UUID.randomUUID())
        val command = UpsertProductCommand("Taken", "d", 1, "u", UUID.randomUUID())

        whenever(productRepository.findById(id)).thenReturn(existing)
        whenever(productRepository.existsByName(command.name)).thenReturn(true)

        assertThrows(DomainAlreadyExistsException::class.java) {
            useCase.execute(Pair(id, command))
        }
    }

    @Test
    fun shouldThrowIllegalArgumentExceptionWhenCategoryInvalid() {
        val id = UUID.randomUUID()
        val existing = Product(id, "Old", "old", 1, "u", UUID.randomUUID())
        val newCategory = UUID.randomUUID()
        val command = UpsertProductCommand("Old", "d", 1, "u", newCategory)

        whenever(productRepository.findById(id)).thenReturn(existing)
        whenever(productRepository.existsByName(command.name)).thenReturn(false)
        whenever(categoryRepository.existsById(newCategory)).thenReturn(false)

        assertThrows(IllegalArgumentException::class.java) {
            useCase.execute(Pair(id, command))
        }
    }
}
