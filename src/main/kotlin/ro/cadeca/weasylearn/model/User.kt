package ro.cadeca.weasylearn.model

import java.util.*

open class User(
        var username: String,
        var firstName: String? = null,
        var lastName: String? = null,
        var dateOfBirth: Date? = null,
        var profilePicture: Long? = null,
        var email: String? = null
)
