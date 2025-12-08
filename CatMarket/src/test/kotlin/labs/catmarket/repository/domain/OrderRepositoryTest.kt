package labs.catmarket.repository.domain

import labs.catmarket.domain.Order
import labs.catmarket.mapper.CustomOrderEntityMapper
import labs.catmarket.repository.OrderJpaRepository
import labs.catmarket.repository.domainImpl.order.OrderRepositoryImpl
import labs.catmarket.repository.entity.OrderEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
@DisplayName("OrderRepository Unit Test")
class OrderRepositoryTest {

    @Mock
    lateinit var orderJpaRepository: OrderJpaRepository

    @Mock
    lateinit var customOrderEntityMapper: CustomOrderEntityMapper

    @InjectMocks
    lateinit var repository: OrderRepositoryImpl

    private fun createOrder(id: UUID) =
        Order(id, LocalDateTime.now(), labs.catmarket.domain.Status.NEW, emptyList())

    @Test
    fun shouldSaveOrder() {
        val id = UUID.randomUUID()
        val domain = createOrder(id)

        val entityToSave = mock<OrderEntity>()
        val savedEntity = mock<OrderEntity>()
        val mappedBackDomain = createOrder(id)

        whenever(customOrderEntityMapper.toEntityFromDomain(domain)).thenReturn(entityToSave)
        whenever(orderJpaRepository.save(entityToSave)).thenReturn(savedEntity)
        whenever(customOrderEntityMapper.toDomainFromEntity(savedEntity)).thenReturn(mappedBackDomain)

        val result = repository.save(domain)

        assertEquals(mappedBackDomain, result)

        verify(customOrderEntityMapper).toEntityFromDomain(domain)
        verify(orderJpaRepository).save(entityToSave)
        verify(customOrderEntityMapper).toDomainFromEntity(savedEntity)
        verifyNoMoreInteractions(orderJpaRepository, customOrderEntityMapper)
    }

    @Test
    fun shouldReturnAllOrders() {
        val entity1 = mock<OrderEntity>()
        val entity2 = mock<OrderEntity>()
        val domain1 = createOrder(UUID.randomUUID())
        val domain2 = createOrder(UUID.randomUUID())

        whenever(orderJpaRepository.findAll()).thenReturn(listOf(entity1, entity2))
        whenever(customOrderEntityMapper.toDomainFromEntity(entity1)).thenReturn(domain1)
        whenever(customOrderEntityMapper.toDomainFromEntity(entity2)).thenReturn(domain2)

        val result = repository.findAll()

        assertEquals(2, result.size)

        verify(orderJpaRepository).findAll()
        verify(customOrderEntityMapper).toDomainFromEntity(entity1)
        verify(customOrderEntityMapper).toDomainFromEntity(entity2)
        verifyNoMoreInteractions(orderJpaRepository, customOrderEntityMapper)
    }

    @Test
    fun shouldReturnOrderWhenFoundById() {
        val id = UUID.randomUUID()
        val entity = mock<OrderEntity>()
        val domain = createOrder(id)

        whenever(orderJpaRepository.findByNaturalId(id)).thenReturn(Optional.of(entity))
        whenever(customOrderEntityMapper.toDomainFromEntity(entity)).thenReturn(domain)

        val result = repository.findById(id)

        assertEquals(domain, result)

        verify(orderJpaRepository).findByNaturalId(id)
        verify(customOrderEntityMapper).toDomainFromEntity(entity)
        verifyNoMoreInteractions(orderJpaRepository, customOrderEntityMapper)
    }

    @Test
    fun shouldReturnNullWhenNotFoundById() {
        val id = UUID.randomUUID()

        whenever(orderJpaRepository.findByNaturalId(id)).thenReturn(Optional.empty())

        val result = repository.findById(id)

        assertNull(result)

        verify(orderJpaRepository).findByNaturalId(id)
        verifyNoMoreInteractions(orderJpaRepository)
        verifyNoInteractions(customOrderEntityMapper)
    }

    @Test
    fun shouldDeleteById() {
        val id = UUID.randomUUID()

        repository.deleteById(id)

        verify(orderJpaRepository).deleteByNaturalId(id)
        verifyNoMoreInteractions(orderJpaRepository)
        verifyNoInteractions(customOrderEntityMapper)
    }

    @Test
    fun shouldReturnTrueWhenExistsById() {
        val id = UUID.randomUUID()

        whenever(orderJpaRepository.existsByNaturalId(id)).thenReturn(true)

        val result = repository.existsById(id)

        assertTrue(result)

        verify(orderJpaRepository).existsByNaturalId(id)
        verifyNoMoreInteractions(orderJpaRepository)
        verifyNoInteractions(customOrderEntityMapper)
    }

    @Test
    fun shouldDeleteAll() {
        repository.deleteAll()

        verify(orderJpaRepository).deleteAll()
        verifyNoMoreInteractions(orderJpaRepository)
        verifyNoInteractions(customOrderEntityMapper)
    }
}
