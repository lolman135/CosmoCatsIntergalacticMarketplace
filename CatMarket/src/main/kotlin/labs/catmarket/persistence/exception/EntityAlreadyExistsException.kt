package labs.catmarket.persistence.exception

class EntityAlreadyExistsException(override val message: String) : IllegalArgumentException(message)