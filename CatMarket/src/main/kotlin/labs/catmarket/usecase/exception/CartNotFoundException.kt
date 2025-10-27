package labs.catmarket.usecase.exception

class CartNotFoundException() : IllegalArgumentException() {
    override val message: String
        get() = "Cart for this user not found"
}