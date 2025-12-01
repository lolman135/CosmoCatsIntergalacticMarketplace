package labs.catmarket.application.category

import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.useCase.category.CreateCategoryUseCase
import labs.catmarket.application.useCase.category.UpsertCategoryCommand
import labs.catmarket.domain.Category
import labs.catmarket.repository.domainImpl.category.CategoryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@ExtendWith(MockitoExtension::class)
@DisplayName("Create Category Use Case Test")
class CreateCategoryUseCaseTest {

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var useCase: CreateCategoryUseCase

    @Test
    fun shouldCreateCategoryWhenNameUnique() {
        val name = "Electronics"
        whenever(categoryRepository.existsByName(name)).thenReturn(false)
        whenever(categoryRepository.save(any())).thenAnswer { it.getArgument<Category>(0) }

        val result = useCase.execute(UpsertCategoryCommand(name))

        val captor = argumentCaptor<Category>()
        verify(categoryRepository).existsByName(name)
        verify(categoryRepository).save(captor.capture())
        verifyNoMoreInteractions(categoryRepository)

        assertEquals(name, captor.firstValue.name)
        assertNotNull(captor.firstValue.id)
        assertEquals(name, result.name)
        assertNotNull(result.id)
    }

    @Test
    fun shouldThrowWhenNameAlreadyExists() {
        val name = "Electronics"
        whenever(categoryRepository.existsByName(name)).thenReturn(true)

        assertThrows(DomainAlreadyExistsException::class.java) {
            useCase.execute(UpsertCategoryCommand(name))
        }

        verify(categoryRepository).existsByName(name)
        verifyNoMoreInteractions(categoryRepository)
    }
}
