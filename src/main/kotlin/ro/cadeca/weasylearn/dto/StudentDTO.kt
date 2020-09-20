package ro.cadeca.weasylearn.dto

import ro.cadeca.weasylearn.persistence.subject.SubjectEntity
import java.util.*

class StudentDTO(userName: String,
                 firstName: String?,
                 lastName: String?,
                 dateOfBirth: Date?,
                 profilePicture: ByteArray?,
                 email: String?,
                 var studyType: String? = null,
                 var year: Int? = null,
                 var groupId: String? = null,
                 var university: String? = null,
                 var faculty: String? = null,
                 var department: String? = null) :
        UserDTO(
                userName,
                firstName,
                lastName,
                dateOfBirth,
                profilePicture,
                email)
