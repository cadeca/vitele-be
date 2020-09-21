package ro.cadeca.weasylearn.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(value = [Type(value = StudentDTO::class), Type(value = TeacherDTO::class)])
open class UserDTO(
        var username: String,
        var firstName: String? = null,
        var lastName: String? = null,
        var dateOfBirth: Date? = null,
        var profilePicture: ByteArray? = null,
        var email: String? = null
)
