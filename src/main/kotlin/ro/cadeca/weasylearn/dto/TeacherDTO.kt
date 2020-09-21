package ro.cadeca.weasylearn.dto

import java.util.*

class TeacherDTO(username: String,
                 firstName: String?,
                 lastName: String?,
                 dateOfBirth: Date?,
                 profilePicture: ByteArray?,
                 email: String?,
                 var department: String? = null,
                 var titles: List<String>? = null,
                 var eduUser: String? = null,
                 var githubUser: String? = null
) : UserDTO(username,
        firstName,
        lastName,
        dateOfBirth,
        profilePicture,
        email)
