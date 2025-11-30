package labs.catmarket.repository.domainImpl.product

import labs.catmarket.domain.Product
import labs.catmarket.mapper.ProductMapper
import labs.catmarket.mapper.ProductMapperHelper
import labs.catmarket.repository.ProductJpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
    private val productMapper: ProductMapper,
    private val productMapperHelper: ProductMapperHelper
) : ProductRepository {

    override fun existsByName(name: String) = productJpaRepository.existsProductEntityByName(name)

    override fun save(domain: Product): Product {
        val entity = productMapper.toEntityFromDomain(domain, productMapperHelper)
        return productMapper.toDomainFromEntity(productJpaRepository.save(entity))
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