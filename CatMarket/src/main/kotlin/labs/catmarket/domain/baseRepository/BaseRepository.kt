package labs.catmarket.domain.baseRepository

interface BaseRepository<ID, T> {
    fun save(domain: T): T
    fun findAll(): List<T>
    fun findById(id: ID): T
    fun deleteById(id: ID)
    fun existsById(id: ID): Boolean
}