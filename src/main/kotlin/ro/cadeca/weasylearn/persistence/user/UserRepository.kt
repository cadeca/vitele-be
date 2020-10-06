package ro.cadeca.weasylearn.persistence.user

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserDocument, String> {
    fun findByUsername(username: String): UserDocument?
    fun findByType(type: String): List<UserDocument>

    fun existsByUsername(username: String): Boolean

    fun existsByUsernameAndType(username: String, type: String): Boolean

    fun findByFullNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(fnQuery: String, unQuery: String, emQuery: String): List<UserDocument>
}
