package labs.catmarket.dto.outbound

data class GitHubUserDtoOutbound(
    val id: Long,
    val login: String,
    val name: String?,
)