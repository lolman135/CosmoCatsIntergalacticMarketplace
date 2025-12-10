package labs.catmarket.config.noAuth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Profile("no-auth")
@Configuration
@EnableWebSecurity
class NoAuthSecurityConfig {

    @Bean
    fun noAuthSecurityFilterChain(http: HttpSecurity): SecurityFilterChain{
        http{
            csrf { disable() }
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
        }

        http.oauth2Login { it.disable() }
        http.oauth2ResourceServer { it.disable() }

        return http.build()
    }
}