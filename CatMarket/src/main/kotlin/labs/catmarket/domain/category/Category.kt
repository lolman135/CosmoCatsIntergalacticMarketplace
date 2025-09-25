package labs.catmarket.domain.category

import java.util.UUID

data class Category(val id: UUID?, val name: String) {
    init {
        require(name.isNotBlank()){"Name should not be empty"}
    }
}
