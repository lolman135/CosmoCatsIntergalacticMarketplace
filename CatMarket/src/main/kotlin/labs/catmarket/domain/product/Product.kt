package labs.catmarket.domain.product

import java.util.UUID
import kotlin.require

data class Product(
    val id: UUID?,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val categoryId: UUID
) {
    init {
        require(name.isNotBlank()){"Name should not be empty"}
        require(price >= 0){"Price should not be less than 0"}
        require(imageUrl.isNotBlank()){"Url should not be empty"}
    }

    fun rename(newName: String): Product{
        require(newName.isNotBlank()){"Name should not be empty"}
        return copy(name = newName)
    }

    fun changePrice(newPrice: Int): Product{
        require(newPrice >= 0){"Price should not be less than 0"}
        return copy(price = newPrice)
    }

    fun changeUrl(newImageUrl: String): Product{
        require(newImageUrl.isNotBlank()){"Url should not be empty"}
        return copy(imageUrl = newImageUrl)
    }
}
