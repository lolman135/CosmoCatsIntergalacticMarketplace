package labs.catmarket.repository.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.hibernate.annotations.NaturalId
import java.util.UUID

@Entity
@Table(name = "products")
class ProductEntity (

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @get:SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 50)
    var id: Long? = null,

    @get:Column(name = "business_id")
    @get:NaturalId
    var businessId: UUID,
    var name: String,
    var description: String,
    var price: Int,

    @get:Column(name = "image_url")
    var imageUrl: String,

    @get:ManyToOne
    @get:JoinColumn(name = "category_id", referencedColumnName = "id")
    var category: CategoryEntity
)
{
    // See CategoryEntity.kt explanations
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductEntity

        return businessId == other.businessId
    }

    override fun hashCode(): Int {
        return businessId.hashCode()
    }
}