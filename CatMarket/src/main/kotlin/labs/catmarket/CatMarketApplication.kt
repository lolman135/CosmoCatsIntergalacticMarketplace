package labs.catmarket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class CatMarketApplication

fun main(args: Array<String>) {
    runApplication<CatMarketApplication>(*args)
}
