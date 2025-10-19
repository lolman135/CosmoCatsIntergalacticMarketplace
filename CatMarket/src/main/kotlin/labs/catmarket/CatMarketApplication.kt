package labs.catmarket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatMarketApplication

fun main(args: Array<String>) {
    runApplication<CatMarketApplication>(*args)
}
