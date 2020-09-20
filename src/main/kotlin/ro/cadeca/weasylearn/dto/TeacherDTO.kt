package ro.cadeca.weasylearn.dto

import java.util.*

class TeacherDTO(userName: String,
                 firstName: String?,
                 lastName: String?,
                 dateOfBirth: Date?,
                 profilePicture: ByteArray?,
                 email: String?,
                 var department: String? = null,
                 var titles: List<String>? = null,
                 var eduUser: String? = null,
                 var githubUser: String? = null
) : UserDTO(userName,
        firstName,
        lastName,
        dateOfBirth,
        profilePicture,
        email)
