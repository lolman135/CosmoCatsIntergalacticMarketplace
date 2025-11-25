package labs.catmarket.application.category

import labs.catmarket.application.useCase.category.GetAllCategoriesUseCase
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
@DisplayName("Get All Categories Use Case Test")
class GetAllCategoriesUseCaseTest {

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var useCase: GetAllCategoriesUseCase

    @Test
    fun shouldReturnAllCategories() {
        val list = listOf(
            Category(UUID.randomUUID(), "A"),
            Category(UUID.randomUUID(), "B")
        )
        whenever(categoryRepository.findAll()).thenReturn(list)

        val result = useCase.execute(Unit)

        assertEquals(list, result)
        verify(categoryRepository).findAll()
        verifyNoMoreInteractions(categoryRepository)
    }

    @Test
    fun shouldReturnEmptyListWhenNoCategories() {
        whenever(categoryRepository.findAll()).thenReturn(emptyList())

        val result = useCase.execute(Unit)

        assertTrue(result.isEmpty())
        verify(categoryRepository).findAll()
        verifyNoMoreInteractions(categoryRepository)
    }
}