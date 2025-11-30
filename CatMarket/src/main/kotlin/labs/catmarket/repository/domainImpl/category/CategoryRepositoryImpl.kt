package labs.catmarket.repository.domainImpl.category

import labs.catmarket.domain.Category
import labs.catmarket.mapper.CategoryMapper
import labs.catmarket.repository.CategoryJpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CategoryRepositoryImpl(
    private val categoryJpaRepository: CategoryJpaRepository,
    private val categoryMapper: CategoryMapper
) : CategoryRepository {

    override fun existsByName(name: String) = categoryJpaRepository.existsCategoryEntityByName(name)

    override fun save(domain: Category): Category {
        val categoryEntity = categoryMapper.toEntityFromDomain(domain)
        return categoryMapper.toDomainFromEntity(categoryJpaRepository.save(categoryEntity))
    }

    override fun findAll() = categoryJpaRepository.findAll().map { categoryMapper.toDomainFromEntity(it) }

    override fun findById(id: UUID): Category? {
        val optionalCategoryEntity = categoryJpaRepository.findByNaturalId(id)
        return optionalCategoryEntity.map { categoryMapper.toDomainFromEntity(it) }.orElse(null)
    }

    override fun deleteById(id: UUID) {
        categoryJpaRepository.deleteByNaturalId(id)
    }

    override fun existsById(id: UUID) = categoryJpaRepository.existsByNaturalId(id)

    override fun deleteAll() {
        categoryJpaRepository.deleteAll()
    }
}