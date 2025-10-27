package labs.catmarket.usecase.useCase

interface UseCase<C, R> {
    fun execute(command: C): R
}