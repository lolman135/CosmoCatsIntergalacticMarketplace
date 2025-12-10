package labs.catmarket.web.controller

import labs.catmarket.dto.outbound.GitHubUserDtoOutbound
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/github-user")
class GitHubUserController {

    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal user: OAuth2User): ResponseEntity<GitHubUserDtoOutbound>{
        val response = GitHubUserDtoOutbound(
            id = user.getAttribute<Any>("id")!!.toString().toLong(),
            login = user.getAttribute<String>("login") ?: "unknown",
            name = user.getAttribute<String>("name"),
        )
        return ResponseEntity.ok(response)
    }
}