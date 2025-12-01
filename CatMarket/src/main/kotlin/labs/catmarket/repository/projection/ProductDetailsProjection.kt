package labs.catmarket.repository.projection

interface ProductDetailsProjection {

    fun getName(): String
    fun getDescription(): String
    fun getPrice(): Int
}