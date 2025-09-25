package labs.catmarket.persistence.exception

class EntityNotFoundException(override val message: String) : IllegalArgumentException(message) {
}