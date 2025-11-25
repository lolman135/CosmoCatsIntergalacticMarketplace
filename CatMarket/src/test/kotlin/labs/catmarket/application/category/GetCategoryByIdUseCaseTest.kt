package labs.catmarket.application.category

import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.category.GetCategoryByIdUseCase
import labs.catmarket.domain.Category
import labs.catmarket.repository.domainrepository.category.CategoryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import java.util.UUID

@ExtendWith(MockitoExtension::class)
@DisplayName("Get Category By Id Use Case Test")
class GetCategoryByIdUseCaseTest {

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var useCase: GetCategoryByIdUseCase

    @Test
    fun shouldReturnCategoryWhenExists() {
        val id = UUID.randomUUID()
        val category = Category(id, "Books")
        whenever(categoryRepository.findById(id)).thenReturn(category)

        val result = useCase.execute(id)

        assertSame(category, result)
        verify(categoryRepository).findById(id)
        verifyNoMoreInteractions(categoryRepository)
    }

    @Test
    fun shouldThrowWhenCategoryNotFound() {
        val id = UUID.randomUUID()
        whenever(categoryRepository.findById(id)).thenReturn(null)

        val ex = assertThrows(DomainNotFoundException::class.java) {
            useCase.execute(id)
        }
        assertTrue(ex.message?.contains("Category") == true)

        verify(categoryRepository).findById(id)
        verifyNoMoreInteractions(categoryRepository)
    }
}