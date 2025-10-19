package labs.catmarket.application.exception

import java.util.UUID

class CartNotFoundException() : IllegalArgumentException() {
    override val message: String
        get() = "Cart for this user not found"
}