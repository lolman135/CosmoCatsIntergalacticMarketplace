package labs.catmarket

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
abstract class AbstractIT {

    //for now, it will be empty class only for future db integration (or WireMock setting up ext.)

    //    ⠀⠀⠀⠀⠀⠀⠀⣠⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⣼⡟⠉⠉⠀⠀⠀⠀⢀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⢿⣇⠀⠀⠀⠀⣠⣶⣿⠿⣿⣿⡿⣷⡀⠸⣿⣶⡀⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⠘⢿⣆⠀⣠⣾⣿⣿⣿⣶⣿⣿⣶⣿⠁⠀⣠⣿⡇⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢛⣁⣤⣴⣿⠟⠁⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠋⠁⠀⠀⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⠀⣿⣿⡟⠉⠉⠀⠀⠈⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⢸⣿⣿⠁⠀⠀⠀⠀⠀⢻⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⣾⣿⠇⠀⠀⠀⠀⠀⠀⠀⢿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    //    ⠀⠀⠀⠀⠀⠀⠹⢿⠁⡀⠀⠀⠀⠀⠀⠀⠸⣿⣶⡄
    //
}