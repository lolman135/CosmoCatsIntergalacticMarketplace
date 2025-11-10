package labs.catmarket.application.useCase.cosmoCats

import labs.catmarket.application.useCase.UseCase
import org.springframework.stereotype.Service

@Service
class GetCosmoCatsUseCase : UseCase<Unit, String>{

    override fun execute(command: Unit): String {
        return   "^   ^\n" +
                "( •_• )"
    }
}