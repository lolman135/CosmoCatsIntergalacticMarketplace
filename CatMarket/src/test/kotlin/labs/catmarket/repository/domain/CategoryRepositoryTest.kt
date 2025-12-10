package labs.catmarket.repository.domain

import labs.catmarket.domain.Category
import labs.catmarket.mapper.CategoryMapper
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.domainImpl.category.CategoryRepositoryImpl
import labs.catmarket.repository.entity.CategoryEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

import java.util.*

@ExtendWith(MockitoExtension::class)
@DisplayName("CategoryRepositoryImpl Unit Test")
class CategoryRepositoryTest {

    @Mock
    lateinit var categoryJpaRepository: CategoryJpaRepository

    @Mock
    lateinit var categoryMapper: CategoryMapper

    @InjectMocks
    lateinit var repository: CategoryRepositoryImpl

    @Test
    fun shouldReturnTrueWhenExistsByName() {
        whenever(categoryJpaRepository.existsCategoryEntityByName("Cats"))
            .thenReturn(true)

        val result = repository.existsByName("Cats")

        assertTrue(result)
        verify(categoryJpaRepository).existsCategoryEntityByName("Cats")
        verifyNoMoreInteractions(categoryJpaRepository, categoryMapper)
    }

    @Test
    fun shouldSaveNewCategoryWhenEntityNotExists() {
        val id = UUID.randomUUID()
        val domain = Category(id, "Cats")

        whenever(categoryJpaRepository.findByNaturalId(id)).thenReturn(Optional.empty())

        val entity = mock<CategoryEntity>()
        val savedEntity = mock<CategoryEntity>()
        val mappedBackDomain = Category(id, "Cats")

        whenever(categoryMapper.toEntityFromDomain(domain)).thenReturn(entity)
        whenever(categoryJpaRepository.save(entity)).thenReturn(savedEntity)
        whenever(categoryMapper.toDomainFromEntity(savedEntity)).thenReturn(mappedBackDomain)

        val result = repository.save(domain)

        assertEquals(mappedBackDomain, result)

        verify(categoryJpaRepository).findByNaturalId(id)
        verify(categoryMapper).toEntityFromDomain(domain)
        verify(categoryJpaRepository).save(entity)
        verify(categoryMapper).toDomainFromEntity(savedEntity)
        verifyNoMoreInteractions(categoryJpaRepository, categoryMapper)
    }

    @Test
    fun shouldUpdateExistingCategoryWhenEntityExists() {
        val id = UUID.randomUUID()
        val domain = Category(id, "NewName")

        val existingEntity = CategoryEntity(
            id = 1L,
            businessId = id,
            name = "OldName"
        )

        whenever(categoryJpaRepository.findByNaturalId(id))
            .thenReturn(Optional.of(existingEntity))

        val updatedDomain = Category(id, "NewName")
        whenever(categoryMapper.toDomainFromEntity(existingEntity))
            .thenReturn(updatedDomain)

        val result = repository.save(domain)

        assertEquals("NewName", existingEntity.name)
        assertEquals(updatedDomain, result)

        verify(categoryJpaRepository).findByNaturalId(id)
        verify(categoryMapper).toDomainFromEntity(existingEntity)
        verifyNoMoreInteractions(categoryJpaRepository, categoryMapper)
    }

    @Test
    fun shouldReturnAllCategories() {
        val entity1 = mock<CategoryEntity>()
        val entity2 = mock<CategoryEntity>()

        whenever(categoryJpaRepository.findAll()).thenReturn(listOf(entity1, entity2))
        whenever(categoryMapper.toDomainFromEntity(entity1)).thenReturn(Category(UUID.randomUUID(), "A"))
        whenever(categoryMapper.toDomainFromEntity(entity2)).thenReturn(Category(UUID.randomUUID(), "B"))

        val result = repository.findAll()

        assertEquals(2, result.size)

        verify(categoryJpaRepository).findAll()
        verify(categoryMapper).toDomainFromEntity(entity1)
        verify(categoryMapper).toDomainFromEntity(entity2)
        verifyNoMoreInteractions(categoryJpaRepository, categoryMapper)
    }

    @Test
    fun shouldReturnCategoryWhenFoundById() {
        val id = UUID.randomUUID()
        val entity = mock<CategoryEntity>()
        val mapped = Category(id, "Cats")

        whenever(categoryJpaRepository.findByNaturalId(id)).thenReturn(Optional.of(entity))
        whenever(categoryMapper.toDomainFromEntity(entity)).thenReturn(mapped)

        val result = repository.findById(id)

        assertEquals(mapped, result)

        verify(categoryJpaRepository).findByNaturalId(id)
        verify(categoryMapper).toDomainFromEntity(entity)
        verifyNoMoreInteractions(categoryJpaRepository, categoryMapper)
    }

    @Test
    fun shouldReturnNullWhenNotFoundById() {
        val id = UUID.randomUUID()

        whenever(categoryJpaRepository.findByNaturalId(id)).thenReturn(Optional.empty())

        val result = repository.findById(id)

        assertNull(result)

        verify(categoryJpaRepository).findByNaturalId(id)
        verifyNoMoreInteractions(categoryJpaRepository)
        verifyNoInteractions(categoryMapper)
    }

    @Test
    fun shouldDeleteById() {
        val id = UUID.randomUUID()

        repository.deleteById(id)

        verify(categoryJpaRepository).deleteByNaturalId(id)
        verifyNoMoreInteractions(categoryJpaRepository)
        verifyNoInteractions(categoryMapper)
    }

    @Test
    fun shouldReturnTrueWhenExistsById() {
        val id = UUID.randomUUID()

        whenever(categoryJpaRepository.existsByNaturalId(id)).thenReturn(true)

        val result = repository.existsById(id)

        assertTrue(result)

        verify(categoryJpaRepository).existsByNaturalId(id)
        verifyNoMoreInteractions(categoryJpaRepository)
        verifyNoInteractions(categoryMapper)
    }

    @Test
    fun shouldDeleteAll() {
        repository.deleteAll()

        verify(categoryJpaRepository).deleteAll()
        verifyNoMoreInteractions(categoryJpaRepository)
        verifyNoInteractions(categoryMapper)
    }
}
