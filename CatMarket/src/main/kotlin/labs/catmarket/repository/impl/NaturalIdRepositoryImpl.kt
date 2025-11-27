package labs.catmarket.repository.impl

import jakarta.persistence.EntityManager
import labs.catmarket.repository.NaturalIdRepository
import org.hibernate.Session
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import java.io.Serializable
import java.util.Optional

class NaturalIdRepositoryImpl<T, ID : Serializable>(
    entityInfo: JpaEntityInformation<T, *>,
    private val entityManager: EntityManager
) : SimpleJpaRepository<T, ID>(entityInfo, entityManager), NaturalIdRepository<T, ID>{

    override fun findByNaturalId(naturalId: ID): Optional<T> {
        return entityManager
            .unwrap(Session::class.java)
            .bySimpleNaturalId(this.domainClass)
            .loadOptional(naturalId)
    }

    override fun deleteByNaturalId(naturalId: ID) {
        findByNaturalId(naturalId).ifPresent(this::delete)
    }

    override fun existsByNaturalId(naturalId: ID): Boolean {
        return findByNaturalId(naturalId).isPresent
    }
}