package labs.catmarket.repository

import java.io.Serializable
import java.util.Optional

interface NaturalIdRepository<T, NID : Serializable> {
    fun findByNaturalId(naturalId: NID): Optional<T>
    fun deleteByNaturalId(naturalId: NID)
    fun existsByNaturalId(naturalId: NID): Boolean
}