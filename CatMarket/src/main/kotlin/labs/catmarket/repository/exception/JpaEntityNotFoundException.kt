package labs.catmarket.repository.exception

import java.util.UUID

class JpaEntityNotFoundException(val name: String, val id: UUID) : IllegalArgumentException() {
    override val message: String
        get() = "$name entity with id=$id not found"
}