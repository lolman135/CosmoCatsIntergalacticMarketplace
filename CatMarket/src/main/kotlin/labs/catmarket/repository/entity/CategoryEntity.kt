package labs.catmarket.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.hibernate.annotations.NaturalId
import java.util.UUID

@Entity
@Table(name = "categories")
class CategoryEntity(

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    @get:SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 50)
    var id: Long? = null,

    @get:Column(name = "business_id")
    @get:NaturalId
    var businessId: UUID,

    var name: String
) {
    // Due to many advices of advanced kotlin developers, for correct work of JPA entities in kotlin
    // is recommended to override equals() and hashCode() by hands and only by unique and unchangeable
    // fields. In context of this app, for tables here is one field that does not going to change
    // along the lifecycle of entity: businessId. So equals() and hashCode() uses only this one
    // field.
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryEntity

        return businessId == other.businessId
    }

    override fun hashCode(): Int {
        return businessId.hashCode()
    }
}
