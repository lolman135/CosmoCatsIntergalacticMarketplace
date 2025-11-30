package labs.catmarket.repository.domain

import labs.catmarket.domain.Product
import labs.catmarket.mapper.ProductMapper
import labs.catmarket.mapper.ProductMapperHelper
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.ProductJpaRepository
import labs.catmarket.repository.domainImpl.product.ProductRepositoryImpl
import labs.catmarket.repository.entity.CategoryEntity
import labs.catmarket.repository.entity.ProductEntity
import labs.catmarket.repository.exception.JpaEntityNotFoundException
import labs.catmarket.repository.projection.ProductDetailsProjection
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.util.*

@ExtendWith(MockitoExtension::class)
@DisplayName("ProductRepository Unit Test")
class ProductRepositoryTest {

    @Mock
    lateinit var productJpaRepository: ProductJpaRepository

    @Mock
    lateinit var productMapper: ProductMapper

    @Mock
    lateinit var productMapperHelper: ProductMapperHelper

    @Mock
    lateinit var categoryJpaRepository: CategoryJpaRepository

    @InjectMocks
    lateinit var repository: ProductRepositoryImpl

    private fun createDomainProduct(id: UUID, categoryId: UUID) =
        Product(id, "Name", "Desc", 100, "url", categoryId)

    @Test
    fun shouldReturnTrueWhenExistsByName() {
        whenever(productJpaRepository.existsProductEntityByName("Hat")).thenReturn(true)

        val result = repository.existsByName("Hat")

        assertTrue(result)
        verify(productJpaRepository).existsProductEntityByName("Hat")
        verifyNoMoreInteractions(productJpaRepository, productMapper, categoryJpaRepository)
    }

    @Test
    fun shouldSaveNewProductWhenEntityNotExists() {
        val id = UUID.randomUUID()
        val categoryId = UUID.randomUUID()
        val domain = createDomainProduct(id, categoryId)

        whenever(productJpaRepository.findByNaturalId(id)).thenReturn(Optional.empty())

        val entityToSave = mock<ProductEntity>()
        val savedEntity = mock<ProductEntity>()
        val mappedBackDomain = createDomainProduct(id, categoryId)

        whenever(productMapper.toEntityFromDomain(domain, productMapperHelper)).thenReturn(entityToSave)
        whenever(productJpaRepository.save(entityToSave)).thenReturn(savedEntity)
        whenever(productMapper.toDomainFromEntity(savedEntity)).thenReturn(mappedBackDomain)

        val result = repository.save(domain)

        assertEquals(mappedBackDomain, result)

        verify(productJpaRepository).findByNaturalId(id)
        verify(productMapper).toEntityFromDomain(domain, productMapperHelper)
        verify(productJpaRepository).save(entityToSave)
        verify(productMapper).toDomainFromEntity(savedEntity)
        verifyNoMoreInteractions(productJpaRepository, productMapper, categoryJpaRepository)
    }

    @Test
    fun shouldUpdateExistingProductWhenEntityExists() {
        val id = UUID.randomUUID()
        val categoryId = UUID.randomUUID()
        val domain = createDomainProduct(id, categoryId)

        val existingEntity = ProductEntity(
            id = 1L,
            businessId = id,
            name = "OldName",
            description = "OldDesc",
            price = 50,
            imageUrl = "old",
            category = CategoryEntity(1L, categoryId, "OldCat")
        )

        whenever(productJpaRepository.findByNaturalId(id)).thenReturn(Optional.of(existingEntity))

        val newCategoryEntity = CategoryEntity(2L, categoryId, "NewCat")
        whenever(categoryJpaRepository.findByNaturalId(categoryId))
            .thenReturn(Optional.of(newCategoryEntity))

        val mappedBackDomain = createDomainProduct(id, categoryId)
        whenever(productMapper.toDomainFromEntity(existingEntity)).thenReturn(mappedBackDomain)

        val result = repository.save(domain)

        assertEquals(mappedBackDomain, result)
        assertEquals("Name", existingEntity.name)
        assertEquals("Desc", existingEntity.description)
        assertEquals(100, existingEntity.price)
        assertEquals("url", existingEntity.imageUrl)
        assertEquals(newCategoryEntity, existingEntity.category)

        verify(productJpaRepository).findByNaturalId(id)
        verify(categoryJpaRepository).findByNaturalId(categoryId)
        verify(productMapper).toDomainFromEntity(existingEntity)
        verifyNoMoreInteractions(productJpaRepository, productMapper, categoryJpaRepository)
    }

    @Test
    fun shouldThrowWhenCategoryNotFoundOnUpdate() {
        val id = UUID.randomUUID()
        val categoryId = UUID.randomUUID()
        val domain = createDomainProduct(id, categoryId)

        val existingEntity = ProductEntity(
            id = 1L,
            businessId = id,
            name = "Old",
            description = "OldD",
            price = 10,
            imageUrl = "img",
            category = CategoryEntity(1L, categoryId, "Cat")
        )

        whenever(productJpaRepository.findByNaturalId(id)).thenReturn(Optional.of(existingEntity))
        whenever(categoryJpaRepository.findByNaturalId(categoryId)).thenReturn(Optional.empty())

        assertThrows<JpaEntityNotFoundException> { repository.save(domain) }

        verify(productJpaRepository).findByNaturalId(id)
        verify(categoryJpaRepository).findByNaturalId(categoryId)
        verifyNoMoreInteractions(productJpaRepository, categoryJpaRepository)
        verifyNoInteractions(productMapper)
    }

    @Test
    fun shouldReturnAllProducts() {
        val entity1 = mock<ProductEntity>()
        val entity2 = mock<ProductEntity>()

        whenever(productJpaRepository.findAll()).thenReturn(listOf(entity1, entity2))
        whenever(productMapper.toDomainFromEntity(entity1)).thenReturn(createDomainProduct(UUID.randomUUID(), UUID.randomUUID()))
        whenever(productMapper.toDomainFromEntity(entity2)).thenReturn(createDomainProduct(UUID.randomUUID(), UUID.randomUUID()))

        val result = repository.findAll()

        assertEquals(2, result.size)

        verify(productJpaRepository).findAll()
        verify(productMapper).toDomainFromEntity(entity1)
        verify(productMapper).toDomainFromEntity(entity2)
        verifyNoMoreInteractions(productJpaRepository, productMapper, categoryJpaRepository)
    }

    @Test
    fun shouldReturnProductWhenFoundById() {
        val id = UUID.randomUUID()
        val entity = mock<ProductEntity>()
        val mapped = createDomainProduct(id, UUID.randomUUID())

        whenever(productJpaRepository.findByNaturalId(id)).thenReturn(Optional.of(entity))
        whenever(productMapper.toDomainFromEntity(entity)).thenReturn(mapped)

        val result = repository.findById(id)

        assertEquals(mapped, result)

        verify(productJpaRepository).findByNaturalId(id)
        verify(productMapper).toDomainFromEntity(entity)
        verifyNoMoreInteractions(productJpaRepository, productMapper)
        verifyNoInteractions(categoryJpaRepository)
    }

    @Test
    fun shouldReturnNullWhenNotFoundById() {
        val id = UUID.randomUUID()

        whenever(productJpaRepository.findByNaturalId(id)).thenReturn(Optional.empty())

        val result = repository.findById(id)

        assertNull(result)

        verify(productJpaRepository).findByNaturalId(id)
        verifyNoMoreInteractions(productJpaRepository)
        verifyNoInteractions(productMapper, categoryJpaRepository)
    }

    @Test
    fun shouldDeleteById() {
        val id = UUID.randomUUID()

        repository.deleteById(id)

        verify(productJpaRepository).deleteByNaturalId(id)
        verifyNoMoreInteractions(productJpaRepository)
        verifyNoInteractions(productMapper, categoryJpaRepository)
    }

    @Test
    fun shouldReturnTrueWhenExistsById() {
        val id = UUID.randomUUID()

        whenever(productJpaRepository.existsByNaturalId(id)).thenReturn(true)

        val result = repository.existsById(id)

        assertTrue(result)

        verify(productJpaRepository).existsByNaturalId(id)
        verifyNoMoreInteractions(productJpaRepository)
    }

    @Test
    fun shouldDeleteAll() {
        repository.deleteAll()

        verify(productJpaRepository).deleteAll()
        verifyNoInteractions(productMapper, categoryJpaRepository)
    }

    @Test
    fun shouldReturnProjectionWhenFoundByName() {
        val projection = mock<ProductDetailsProjection>()
        whenever(productJpaRepository.findByName("Hat"))
            .thenReturn(Optional.of(projection))

        val result = repository.findProjectionByName("Hat")

        assertEquals(projection, result)

        verify(productJpaRepository).findByName("Hat")
        verifyNoMoreInteractions(productJpaRepository)
        verifyNoInteractions(productMapper, categoryJpaRepository)
    }

    @Test
    fun shouldReturnNullWhenProjectionNotFoundByName() {
        whenever(productJpaRepository.findByName("Hat"))
            .thenReturn(Optional.empty())

        val result = repository.findProjectionByName("Hat")

        assertNull(result)

        verify(productJpaRepository).findByName("Hat")
        verifyNoMoreInteractions(productJpaRepository)
        verifyNoInteractions(productMapper, categoryJpaRepository)
    }
}