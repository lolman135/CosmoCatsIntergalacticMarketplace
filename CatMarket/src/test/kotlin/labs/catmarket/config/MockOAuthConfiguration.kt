package labs.catmarket.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType

class MockOAuthConfiguration {

    @Bean
    @Primary
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val registration = ClientRegistration
            .withRegistrationId("test-client")
            .clientId("test-client-id")
            .clientSecret("test-client-secret")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .authorizationUri("https://mock-oauth.example.com/oauth/authorize")
            .tokenUri("https://mock-oauth.example.com/oauth/token")
            .userInfoUri("https://mock-oauth.example.com/userinfo")
            .userNameAttributeName("sub")
            .clientName("Test Client")
            .build()

        return InMemoryClientRegistrationRepository(registration)
    }
}