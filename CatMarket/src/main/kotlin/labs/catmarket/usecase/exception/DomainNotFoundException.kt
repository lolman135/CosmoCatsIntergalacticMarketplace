package labs.catmarket.usecase.exception

import java.util.UUID

class DomainNotFoundException(val name: String, val id: UUID) : IllegalArgumentException() {

    override val message: String?
        get() = "$name with id=$id not found"
}