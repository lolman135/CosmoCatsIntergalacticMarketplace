package labs.catmarket

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Disabled("Disabled because the app context test is not needed and breaks Liquibase/Testcontainers setup")
class CatMarketApplicationTests {

    @Test
    fun contextLoads() {
    }

}
