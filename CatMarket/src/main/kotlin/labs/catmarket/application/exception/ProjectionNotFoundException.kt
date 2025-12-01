package labs.catmarket.application.exception

class ProjectionNotFoundException(val name: String) : Exception() {

    override val message: String
        get() = "Product projection with name=$name not found"
}