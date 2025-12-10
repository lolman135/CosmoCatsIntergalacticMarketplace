package labs.catmarket.config.security

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import org.springframework.web.client.RestTemplate
import kotlin.apply

// This class is a custom opaque token introspector. It needs to use github access token as bearer token for testing api
// in Postman. Idk if i'm right doing this, but I think, it was a good practice to try implement this mechanism.
// In simple words, this introspector gets a gh token, creates a request and sends it to https://github.api.com, to
// ask GitHub, is this token is valid. If yes, it gets info about user for whom this token was generated and then,
// Spring security allows to make a request. If token is not walid, GitHub Api Returns 401, and spring catch it and
// declines user request with 401 error.
class GitHubOpaqueTokenIntrospector : OpaqueTokenIntrospector {

    override fun introspect(token: String): OAuth2AuthenticatedPrincipal {
        val headers = HttpHeaders().apply {
            accept = listOf(MediaType.APPLICATION_JSON)
            setBearerAuth(token)
            add("User-Agent", "Spring-App")
        }

        val request = HttpEntity<Void>(headers)

        val response = RestTemplate().exchange(
            "https://api.github.com/user",
            HttpMethod.GET,
            request,
            Map::class.java
        )

        if(!response.statusCode.is2xxSuccessful)
            throw OAuth2AuthenticationException(OAuth2Error("401", "Token is invalid", null))

        val attributes = response.body as Map<String, Any?>

        return DefaultOAuth2AuthenticatedPrincipal(attributes, listOf(SimpleGrantedAuthority("ROLE_USER")))

    }
}