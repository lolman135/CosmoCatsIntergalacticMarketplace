package labs.catmarket.application.category

import labs.catmarket.application.exception.DomainAlreadyExistsException
import labs.catmarket.application.exception.DomainNotFoundException
import labs.catmarket.application.useCase.category.UpdateCategoryByIdUseCase
import labs.catmarket.application.useCase.category.UpsertCategoryCommand
import labs.catmarket.domain.Category
import labs.catmarket.repository.domainrepository.category.CategoryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.util.UUID

@ExtendWith(MockitoExtension::class)
@DisplayName("Update Category By Id Use Case Test")
class UpdateCategoryByIdUseCaseTest {

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var useCase: UpdateCategoryByIdUseCase

    @Test
    fun shouldUpdateNameWhenNewNameUnique() {
        val id = UUID.randomUUID()
        val existing = Category(id, "Old")
        val newName = "New"

        whenever(categoryRepository.findById(id)).thenReturn(existing)
        whenever(categoryRepository.existsByName(newName)).thenReturn(false)
        whenever(categoryRepository.save(any())).thenAnswer { it.getArgument<Category>(0) }

        val result = useCase.execute(id to UpsertCategoryCommand(newName))

        val captor = argumentCaptor<Category>()
        verify(categoryRepository).findById(id)
        verify(categoryRepository).existsByName(newName)
        verify(categoryRepository).save(captor.capture())
        verifyNoMoreInteractions(categoryRepository)

        assertEquals(newName, captor.firstValue.name)
        assertEquals(id, captor.firstValue.id)
        assertEquals(newName, result.name)
        assertEquals(id, result.id)
    }

    @Test
    fun shouldThrowWhenCategoryNotFound() {
        val id = UUID.randomUUID()
        whenever(categoryRepository.findById(id)).thenReturn(null)

        assertThrows(DomainNotFoundException::class.java) {
            useCase.execute(id to UpsertCategoryCommand("X"))
        }

        verify(categoryRepository).findById(id)
        verifyNoMoreInteractions(categoryRepository)
    }

    @Test
    fun shouldThrowWhenNewNameAlreadyExistsAndDifferentFromCurrent() {
        val id = UUID.randomUUID()
        val existing = Category(id, "Old")
        val conflictingName = "Taken"

        whenever(categoryRepository.findById(id)).thenReturn(existing)
        whenever(categoryRepository.existsByName(conflictingName)).thenReturn(true)

        assertThrows(DomainAlreadyExistsException::class.java) {
            useCase.execute(id to UpsertCategoryCommand(conflictingName))
        }

        verify(categoryRepository).findById(id)
        verify(categoryRepository).existsByName(conflictingName)
        verify(categoryRepository, never()).save(any())
        verifyNoMoreInteractions(categoryRepository)
    }

    @Test
    fun shouldNotCheckUniquenessWhenNameUnchangedAndSave() {
        val id = UUID.randomUUID()
        val sameName = "Same"
        val existing = Category(id, sameName)

        whenever(categoryRepository.findById(id)).thenReturn(existing)
        whenever(categoryRepository.save(any())).thenAnswer { it.getArgument<Category>(0) }

        val result = useCase.execute(id to UpsertCategoryCommand(sameName))

        verify(categoryRepository).findById(id)
        verify(categoryRepository, never()).existsByName(any())
        verify(categoryRepository).save(any())
        verifyNoMoreInteractions(categoryRepository)

        assertEquals(sameName, result.name)
        assertEquals(id, result.id)
    }
}