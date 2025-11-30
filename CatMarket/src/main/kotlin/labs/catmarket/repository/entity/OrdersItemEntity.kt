package labs.catmarket.repository.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "orders_items")
class OrdersItemEntity(

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_items_id_seq")
    @get:SequenceGenerator(name = "orders_items_id_seq", sequenceName = "orders_items_id_seq", allocationSize = 50)
    var id: Long? = null,

    @get:ManyToOne
    @get:JoinColumn(name = "product_id", referencedColumnName = "id")
    var product: ProductEntity,

    @get:Column(name = "price_per_unit")
    var pricePerUnit: Int,
    var quantity: Int,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "order_id", referencedColumnName = "id")
    var order: OrderEntity
)