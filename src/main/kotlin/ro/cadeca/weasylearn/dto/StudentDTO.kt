package ro.cadeca.weasylearn.dto

import java.util.*

class StudentDTO(username: String,
                 firstName: String?,
                 lastName: String?,
                 dateOfBirth: Date?,
                 profilePicture: ByteArray?,
                 email: String?,
                 var studyType: String? = null,
                 var year: Int? = null,
                 var group: String? = null,
                 var githubUser: String? = null,
                 var facebookUser: String? = null,
                 var eduUser: String? = null) :
        UserDTO(
                username,
                firstName,
                lastName,
                dateOfBirth,
                profilePicture,
                email)
