package ro.cadeca.weasylearn.persistence.user

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UserRepository : MongoRepository<UserDocument, String> {
    fun findByUsername(username: String): UserDocument?
    fun findByType(type: String): List<UserDocument>

    fun existsByUsername(username: String): Boolean

    fun existsByUsernameAndType(username: String, type: String): Boolean

    fun findByFullNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(fnQuery: String, unQuery: String, emQuery: String): List<UserDocument>

    @Query("{ \$and: [ {'type': /?1/}, {\$or: [ { 'username': { \$regex: /?0/, \$options: 'i'} }, { 'fullName': { \$regex: /?0/, \$options: 'i'} }, { 'email': { \$regex: /?0/, \$options: 'i'} }, ] } ] }")
    fun findByQueryAndType(query: String, type: String): List<UserDocument>
}
