package labs.catmarket.repository.domainImpl.product

import labs.catmarket.domain.Product
import labs.catmarket.mapper.ProductMapper
import labs.catmarket.mapper.ProductMapperHelper
import labs.catmarket.repository.CategoryJpaRepository
import labs.catmarket.repository.ProductJpaRepository
import labs.catmarket.repository.exception.JpaEntityNotFoundException
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
    private val productMapper: ProductMapper,
    private val productMapperHelper: ProductMapperHelper,
    private val categoryJpaRepository: CategoryJpaRepository
) : ProductRepository {

    override fun existsByName(name: String) = productJpaRepository.existsProductEntityByName(name)

    override fun save(domain: Product): Product {

        val entity = productJpaRepository.findByNaturalId(domain.id!!)
            .orElse(null)

        if (entity == null){
            val newEntity = productMapper.toEntityFromDomain(domain, productMapperHelper)

            return productMapper.toDomainFromEntity(productJpaRepository.save(newEntity))
        } else  {
            entity.apply {
                name = domain.name
                description = domain.description
                price = domain.price
                imageUrl = domain.imageUrl
                category = categoryJpaRepository.findByNaturalId(domain.categoryId)
                    .orElseThrow{JpaEntityNotFoundException("Category", domain.categoryId)}
            }
            return productMapper.toDomainFromEntity(entity)
        }
    }

    override fun findAll() = productJpaRepository.findAll().map { productMapper.toDomainFromEntity(it) }

    override fun findById(id: UUID): Product? {
        val optionalProductEntity = productJpaRepository.findByNaturalId(id)
        return optionalProductEntity.map { productMapper.toDomainFromEntity(it) }.orElse(null)
    }

    override fun deleteById(id: UUID) {
        productJpaRepository.deleteByNaturalId(id)
    }

    override fun existsById(id: UUID) = productJpaRepository.existsByNaturalId(id)

    override fun deleteAll() {
        productJpaRepository.deleteAll()
    }
}