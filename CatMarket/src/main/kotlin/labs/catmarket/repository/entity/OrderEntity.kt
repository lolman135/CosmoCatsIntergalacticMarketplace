package labs.catmarket.repository.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import labs.catmarket.domain.Status
import org.hibernate.annotations.NaturalId
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "orders")
class OrderEntity(

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @get:SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 50)
    var id: Long? = null,

    @get:Column(name = "business_id")
    @get:NaturalId
    var businessId: UUID,

    @get:Column(name = "created_at")
    var createdAt: LocalDateTime,

    @get:Enumerated(EnumType.STRING)
    var status: Status,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST], orphanRemoval = true, fetch = FetchType.LAZY)
    var items: MutableList<OrdersItemEntity>? = mutableListOf()
)
{
    // See CategoryEntity.kt explanations
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderEntity

        return businessId == other.businessId
    }

    override fun hashCode(): Int {
        return businessId.hashCode()
    }
}