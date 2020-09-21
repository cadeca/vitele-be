package ro.cadeca.weasylearn.dto.subjects

import ro.cadeca.weasylearn.dto.StudentDTO
import ro.cadeca.weasylearn.dto.TeacherDTO
import ro.cadeca.weasylearn.dto.UserDTO

class SubjectDTO(
        var name: String,
        var code: String,
        var description: String? = null,
        var semester: Int? = null,
        var teacher: TeacherDTO? = null,
        var tutors: List<UserDTO>? = null,
        var students: List<StudentDTO>? = null,
        var schedule: List<SubjectScheduleDTO>? = null
)
