package labs.catmarket.application.exception

class EntityAlreadyExistsException(override val message: String) : IllegalArgumentException(message)