package labs.catmarket.application.exception

class DomainAlreadyExistsException(val name: String) : IllegalArgumentException(){
    override val message: String
        get() = "This $name already exists"
}