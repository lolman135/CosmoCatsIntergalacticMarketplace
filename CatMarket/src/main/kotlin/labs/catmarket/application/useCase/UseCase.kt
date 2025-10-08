package labs.catmarket.application.useCase

interface UseCase<C, R> {
    fun execute(command: C): R
}