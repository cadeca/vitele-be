package ro.cadeca.weasylearn.dto

class UserProfileDTO(val username: String,
                     val fullName: String? = null,
                     val firstName: String? = null,
                     val lastName: String? = null,
                     val email: String? = null,
                     val profilePicture: ByteArray? = null,
                     val type: String? = null)
