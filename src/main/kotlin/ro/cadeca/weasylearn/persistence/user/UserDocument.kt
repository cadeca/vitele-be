package ro.cadeca.weasylearn.persistence.user

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import ro.cadeca.weasylearn.persistence.BaseDocument
import java.util.*

@Document
class UserDocument(
        @Indexed(unique = true)
        var username: String,

        var firstName: String? = null,

        var lastName: String? = null,

        var dateOfBirth: Date? = null,

        var profilePicture: ByteArray? = null,

        var email: String? = null,

        var type: String? = null,

        var details: Map<String, Any> = emptyMap()
) : BaseDocument()
