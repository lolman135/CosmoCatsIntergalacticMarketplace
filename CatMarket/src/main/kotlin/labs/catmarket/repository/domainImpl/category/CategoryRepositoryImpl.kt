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
        val entity = categoryJpaRepository.findByNaturalId(domain.id!!)
            .orElse(null)

        return if (entity == null) {
            val newEntity = categoryMapper.toEntityFromDomain(domain)
            categoryMapper.toDomainFromEntity(categoryJpaRepository.save(newEntity))
        } else {
            entity.name = domain.name
            categoryMapper.toDomainFromEntity(entity)
        }
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