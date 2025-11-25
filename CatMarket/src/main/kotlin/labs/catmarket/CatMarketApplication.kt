package labs.catmarket

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.env.Environment

@SpringBootApplication
@EnableAspectJAutoProxy
class CatMarketApplication

fun main(args: Array<String>) {
    runApplication<CatMarketApplication>(*args)
}
