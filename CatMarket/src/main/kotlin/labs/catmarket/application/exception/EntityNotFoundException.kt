package labs.catmarket.application.exception

class EntityNotFoundException(override val message: String) : IllegalArgumentException(message) {
}