package ro.cadeca.weasylearn.dto

import java.util.*

class UserDTO(
        var userName: String,
        var firstName: String? = null,
        var lastName: String? = null,
        var dateOfBirth: Date? = null,
        var profilePicture: ByteArray? = null,
        var email: String? = null
)
