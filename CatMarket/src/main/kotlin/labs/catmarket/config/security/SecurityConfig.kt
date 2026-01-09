package labs.catmarket.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Profile("!no-auth")
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun githubIntrospector(
        @Value("\${github.user-info-uri:https://api.github.com/user}")
        userInfoUri: String
    ) = GitHubOpaqueTokenIntrospector(userInfoUri)

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        githubIntrospector: GitHubOpaqueTokenIntrospector
    ) : SecurityFilterChain{

        http{
            csrf { disable() }
            cors { disable() }
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }

            oauth2Login {
                loginPage = "/oauth2/authorization/github"
            }

            oauth2ResourceServer {
                opaqueToken { introspector = githubIntrospector }
            }

            logout {
                logoutSuccessUrl = "/"
            }
        }
        return http.build()
    }

    /*         ME WHEN NO KAFKA (squeeze your eyes) :)

    %%+**+*#**+**#**#*****************************###****+*+**
     %#+++*#*+++***********+************##***********##*++**+**
     @*=*##*==+********************+*************#*******+*****
    .@++#*+==+***********************************#****+=*******
    :@=**=--*******++*****+************+++**************+******
    -@=---*###*****+++****************###***************+******
    .+:=*##*********++++*+**++++++===++++***#***********++*****
     =*****+*+==+***+=+++***+********=+====+**********+*++****+
     -=+==--=+*%@%***#==++*************+++++=++*+++*++++++****:
     +++**%@@@@@@+*#+@@--=+++******+*****+++++++++++++++*****--.
    -@@@@@@@@@#++++*++@@@+---=+======-=+***+++++++++++++****-:@
    :@@@@#*===+++++*#+=:@@@@#*++******+===+**++*+++++++****-=@%
     %@-:==++**********+-:*@@@@@@@@@@@@@@%++**+=++=+++#*#+:-@#+
     #%%=-=************##+=--+#@@@@@@@%%%@@#**++=+==**#*=:#@**@-
     ##%@@+:=+++++***++##**+++=-:-:-*@@@@@%%**++===+##+-:@##@@+
     #%##%@@+-@@%+==*++***#******+++=-:=#@@@@#++=-+*+-:%%%@@*+*
     ######%@@+:@@@@#-+##*****+=++++++++=--*@@++=+*+@@@@@@*+*##
    .#%##%%%@   @@@#@-+#*****@@@@%*+==+++++=*%*==+-@@: .#*#####
    .##%%%#@@  @@@*=@==##**%@@::%@@@@@++****##=:-#@*-+#@%######
    .%%%**%@*@:=#*%@@.+#*+#@=  @@@*#@@@%*##%%*.:@@-**%@%#######
    .#=#@@@# %@@@@@@-.##**#@. @@@. @@**@%*%@%--@@++*@@@@**#####
    .@@@%%@@ +*###*-:+#****@: -@@@@@@  @=---:.=+.+@@%%%@@@%*##%
    =@@@@@@@:+++=---+######@@@..**%%=@@#--=- .%@@@@%%%%%%%@@@+*
     +*--**@++#%+=-*###*+*##%@@@%#####=---: -@@%##%%%%%%%#%%%@@.
    =#+*#%+@#*#==+#%%###**+++**###*+--====:@@@%%%%##%%##%%%%#%%-
    -@@@@@@@@===+########***+++++++**####++@#++%%%%%%%@@@###%%%-
    .%@*+**@--+*##**++++*###**##########==@@@-%#*%%%@%--@%%%#%%:
    .#@%##@#:=*#*+====++*+=##****###*#*= @ .+%#****#+@+ @%#%#%%:
    :%%%+%@-+***==---=++=-=###****###*= @@:+*=+****%@@=@@%%%%#%:
    .##@@@--=++==---==--=*@%##***##**= %@@@@@%*#*##*++*+*%@@%%%:
    .%%%: :-------:-=#@@@@%####*#**+- +@@%%%@@%########*++*%%%%:
    .%#@@=+*-:---=*@@#*==++*###**++- *@@%%%@%%%@%%%%%%####*+*##.
    .#*@##=**+**##=:      :=+*##*+- *@@%%%#%%%%%%%%%%*#%%####*#:
      .-:=+=-    .:.                *-:::::::--:----===.---:::.
     */
}