package labs.catmarket.usecase.category

import labs.catmarket.usecase.useCase.category.DeleteCategoryByIdUseCase
import labs.catmarket.repository.category.CategoryRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class DeleteCategoryByIdUseCaseTest {

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var useCase: DeleteCategoryByIdUseCase

    @Test
    fun shouldDeleteCategoryById() {
        val id = UUID.randomUUID()

        useCase.execute(id)

        verify(categoryRepository).deleteById(id)
        verifyNoMoreInteractions(categoryRepository)
    }
}